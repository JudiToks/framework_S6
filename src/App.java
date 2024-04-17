import java.io.File;
import java.sql.Connection;
import java.util.Scanner;

import genesis.Constantes;
import genesis.Credentials;
import genesis.CustomFile;
import genesis.Database;
import genesis.Entity;
import genesis.EntityField;
import genesis.Language;
import handyman.HandyManUtils;

public class App {
    public static void main(String[] args) throws Throwable {
        Database[] databases = HandyManUtils.fromJson(Database[].class,
                HandyManUtils.getFileContent(Constantes.DATABASE_JSON));
        Language[] languages = HandyManUtils.fromJson(Language[].class,
                HandyManUtils.getFileContent(Constantes.LANGUAGE_JSON));
        Database database;
        Language language;
        String databaseName, user, pwd, host;
        boolean useSSL, allowPublicKeyRetrieval;
        String projectName, entityName;
        Credentials credentials;
        String projectNameTagPath, projectNameTagContent;
        File project, credentialFile, project_view;
        String customFilePath, customFileContentOuter;
        Entity[] entities;
        String[] models, controllers;
        String modelFile, controllerFile, customFile;
        String customFileContent;
        String foreignContext;
        String apiHost;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Choose a database engine:");
            for (int i = 0; i < databases.length; i++) {
                System.out.println((i + 1) + ") " + databases[i].getNom());
            }
            System.out.print("> ");
            database = databases[scanner.nextInt() - 1];
            System.out.println("Choose a framework:");
            for (int i = 0; i < languages.length; i++) {
                System.out.println((i + 1) + ") " + languages[i].getNom());
            }
            System.out.print("> ");
            language = languages[scanner.nextInt() - 1];
            System.out.println("Enter your database credentials:");
            System.out.print("Database name: ");
            databaseName = scanner.next();
            System.out.print("Username: ");
            user = scanner.next();
            System.out.print("Password: ");
            pwd = scanner.next();
            System.out.print("Database host: ");
            host = scanner.next();
            System.out.print("Use SSL ?(Y/n): ");
            useSSL = scanner.next().equalsIgnoreCase("Y");
            System.out.print("Allow public key retrieval ?(Y/n): ");
            allowPublicKeyRetrieval = scanner.next().equalsIgnoreCase("Y");
            System.out.println();
            System.out.print("Enter your project name: ");
            projectName = scanner.next();
            System.out.print("Which entities to import ?(* to select all): ");
            entityName = scanner.next();
            credentials = new Credentials(databaseName, user, pwd, host, useSSL, allowPublicKeyRetrieval);
            System.out.print("Enter the host for services (like http://localhost:8080): ");
            apiHost = scanner.next();
            project = new File(projectName + "_back");
            project.mkdir();
            project_view = new File(projectName + "_front");
            project_view.mkdir();
            String project_view_name = projectName + "_front";
            projectName = projectName + "_back";
            for (CustomFile c : language.getAdditionnalFiles()) {
                customFilePath = c.getName();
                customFilePath = customFilePath.replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
                HandyManUtils.createFile(customFilePath);
                customFileContentOuter = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + c.getContent())
                        .replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
                HandyManUtils.overwriteFileContent(customFilePath, customFileContentOuter);
            }
            HandyManUtils.extractDir(
                    Constantes.DATA_PATH + "/" + language.getSkeleton() + "." + Constantes.SKELETON_EXTENSION,
                    project.getPath());
            HandyManUtils.extractDir(
                    Constantes.DATA_PATH + "/" + language.getSkeleton_view() + "." + Constantes.SKELETON_EXTENSION,
                    project_view.getPath());
            credentialFile = new File(project.getPath(), Constantes.CREDENTIAL_FILE);
            credentialFile.createNewFile();
            HandyManUtils.overwriteFileContent(credentialFile.getPath(), HandyManUtils.toJson(credentials));
            for (String replace : language.getProjectNameTags()) {
                projectNameTagPath = replace.replace("[projectNameMaj]", HandyManUtils.majStart(projectName))
                        .replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                projectNameTagContent = HandyManUtils.getFileContent(projectNameTagPath).replace("[projectNameMaj]",
                        HandyManUtils.majStart(projectName));
                projectNameTagContent = projectNameTagContent.replace("[databaseHost]", credentials.getHost());
                projectNameTagContent = projectNameTagContent.replace("[databasePort]", database.getPort());
                projectNameTagContent = projectNameTagContent.replace("[databaseName]", credentials.getDatabaseName());
                projectNameTagContent = projectNameTagContent.replace("[user]", credentials.getUser());
                projectNameTagContent = projectNameTagContent.replace("[pwd]", credentials.getPwd());
                projectNameTagContent = projectNameTagContent.replace("[projectNameMin]",
                        HandyManUtils.minStart(projectName));
                HandyManUtils.overwriteFileContent(projectNameTagPath, projectNameTagContent);
            }
            try (Connection connect = database.getConnexion(credentials)) {
                entities = database.getEntities(connect, credentials, entityName);
                for (int i = 0; i < entities.length; i++) {
                    entities[i].initialize(connect, credentials, database, language);
                }
                models = new String[entities.length];
                controllers = new String[entities.length];
                String vue_paths = project_view_name + "/" + Constantes.ANGULAR_COMPONENT_PATH;
                for (int i = 0; i < models.length; i++) {
                    models[i] = language.generateModel(entities[i], projectName);
                    controllers[i] = language.generateController(entities[i], database, credentials, projectName);
                    modelFile = language.getModel().getModelSavePath().replace("[projectNameMaj]",
                            HandyManUtils.majStart(projectName));
                    controllerFile = language.getController().getControllerSavepath().replace("[projectNameMaj]",
                            HandyManUtils.majStart(projectName));
                    modelFile = modelFile.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                    controllerFile = controllerFile.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                    modelFile += "/" + HandyManUtils.majStart(entities[i].getClassName()) + "."
                            + language.getModel().getModelExtension();
                    controllerFile += "/" + HandyManUtils.majStart(entities[i].getClassName())
                            + language.getController().getControllerNameSuffix() + "."
                            + language.getController().getControllerExtension();
                    HandyManUtils.createFile(modelFile);
                    for (CustomFile f : language.getModel().getModelAdditionnalFiles()) {
                        foreignContext = "";

                        for (EntityField ef : entities[i].getFields()) {
                            if (ef.isForeign()) {
                                foreignContext += language.getModel().getModelForeignContextAttr();
                                foreignContext = foreignContext.replace("[classNameMaj]",
                                        HandyManUtils.majStart(ef.getType()));
                            }
                        }
                        customFile = f.getName().replace("[classNameMaj]",
                                HandyManUtils.majStart(entities[i].getClassName()));
                        customFile = language.getModel().getModelSavePath().replace("[projectNameMaj]",
                                HandyManUtils.majStart(projectName)) + "/" + customFile;
                        customFile = customFile.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
                        customFileContent = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + f.getContent())
                                .replace("[classNameMaj]", HandyManUtils.majStart(entities[i].getClassName()));
                        customFileContent = customFileContent.replace("[projectNameMin]",
                                HandyManUtils.minStart(projectName));
                        customFileContent = customFileContent.replace("[projectNameMaj]",
                                HandyManUtils.majStart(projectName));
                        customFileContent = customFileContent.replace("[classNameMaj]", HandyManUtils.majStart(entities[i].getClassName()));
                        customFileContent = customFileContent.replace("[classNameMin]", HandyManUtils.minStart(entities[i].getClassName()));
                        customFileContent = customFileContent.replace("[primaryIds]", HandyManUtils.minStart(entities[i].getPrimaryField().getName()));
                        customFileContent = customFileContent.replace("[primaryType]", HandyManUtils.majStart(entities[i].getPrimaryField().getType()));
                        customFileContent = customFileContent.replace("[databaseHost]", credentials.getHost());
                        customFileContent = customFileContent.replace("[databaseName]", credentials.getDatabaseName());
                        customFileContent = customFileContent.replace("[user]", credentials.getUser());
                        customFileContent = customFileContent.replace("[pwd]", credentials.getPwd());
                        customFileContent = customFileContent.replace("[modelForeignContextAttr]", foreignContext);
                        HandyManUtils.createFile(customFile);
                        HandyManUtils.overwriteFileContent(customFile, customFileContent);
                    }
                    HandyManUtils.createFile(controllerFile);
                    HandyManUtils.overwriteFileContent(modelFile, models[i]);
                    HandyManUtils.overwriteFileContent(controllerFile, controllers[i]);
                    language.generateAComponent(entities[i], vue_paths, apiHost);
                }
                language.overWriteRoute(entities, vue_paths);
                language.overWriteModule(entities, vue_paths);
                language.generateMenu(entities, vue_paths);
            }
        }
    }
}
