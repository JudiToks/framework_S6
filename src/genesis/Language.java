package genesis;

import java.util.HashMap;

import handyman.HandyManUtils;

public class Language {
    private int id;
    private String nom;
    private HashMap<String, String> syntax;
    private HashMap<String, String> types, typeParsers;
    private String skeleton;
    private String skeleton_view;
    private String[] projectNameTags;
    private CustomFile[] additionnalFiles;
    private Model model;
    private Controller controller;
    private View view;
    private CustomChanges[] customChanges;
    private NavbarLink navbarLinks;

    public NavbarLink getNavbarLinks() {
        return navbarLinks;
    }

    public void setNavbarLinks(NavbarLink navbarLinks) {
        this.navbarLinks = navbarLinks;
    }

    public CustomChanges[] getCustomChanges() {
        return customChanges;
    }

    public void setCustomChanges(CustomChanges[] customChanges) {
        this.customChanges = customChanges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public HashMap<String, String> getSyntax() {
        return syntax;
    }

    public void setSyntax(HashMap<String, String> syntax) {
        this.syntax = syntax;
    }

    public HashMap<String, String> getTypes() {
        return types;
    }

    public void setTypes(HashMap<String, String> types) {
        this.types = types;
    }

    public HashMap<String, String> getTypeParsers() {
        return typeParsers;
    }

    public void setTypeParsers(HashMap<String, String> typeParsers) {
        this.typeParsers = typeParsers;
    }

    public String getSkeleton() {
        return skeleton;
    }

    public void setSkeleton(String skeleton) {
        this.skeleton = skeleton;
    }

    public String getSkeleton_view() {
        return skeleton_view;
    }

    public void setSkeleton_view(String skeleton_view) {
        this.skeleton_view = skeleton_view;
    }

    public String[] getProjectNameTags() {
        return projectNameTags;
    }

    public void setProjectNameTags(String[] projectNameTags) {
        this.projectNameTags = projectNameTags;
    }

    public CustomFile[] getAdditionnalFiles() {
        return additionnalFiles;
    }

    public void setAdditionnalFiles(CustomFile[] additionnalFiles) {
        this.additionnalFiles = additionnalFiles;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String generateModel(Entity entity, String projectName) throws Throwable {
        String content = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + getModel().getModelTemplate() + "." + Constantes.MODEL_TEMPLATE_EXT);
        content = content.replace("[namespace]", getSyntax().get("namespace"));
        content = content.replace("[namespaceStart]", getSyntax().get("namespaceStart"));
        content = content.replace("[namespaceEnd]", getSyntax().get("namespaceEnd"));
        content = content.replace("[package]", getModel().getModelPackage());
        String imports = "";
        for (String i : getModel().getModelImports()) {
            imports += i + "\n";
        }
        content = content.replace("[imports]", imports);
        String annotes = "";
        for (String a : getModel().getModelAnnotations()) {
            annotes += a + "\n";
        }
        content = content.replace("[classAnnotations]", annotes);
        content = content.replace("[extends]", getModel().getModelExtends());
        String constructors = "";
        for (String c : getModel().getModelConstructors()) {
            constructors += c + "\n";
        }
        content = content.replace("[constructors]", constructors);
        String fields = "", fieldAnnotes;
        for (int i = 0; i < entity.getFields().length; i++) {
            fieldAnnotes = "";
            if (entity.getFields()[i].isPrimary()) {
                for (String primAnnote : getModel().getModelPrimaryFieldAnnotations()) {
                    fieldAnnotes += primAnnote + "\n";
                }
            }
            // else if(entity.getFields()[i].isForeign()){
            //     for(int t=0;t<getModel().getModelForeignFieldAnnotations().length;t++){
            //         fieldAnnotes+=getModel().getModelForeignFieldAnnotations()[t]+"\n";
            //     }
            // }
            for (String fa : getModel().getModelFieldAnnotations()) {
                fieldAnnotes += fa + "\n";
            }
            fields += fieldAnnotes;
            fields += getModel().getModelFieldContent() + "\n";
            fields += getModel().getModelGetter() + "\n";
            fields += getModel().getModelSetter() + "\n";
            fields = fields.replace("[columnName]", entity.getColumns()[i].getName());
            fields = fields.replace("[fieldType]", entity.getFields()[i].getType());
            fields = fields.replace("[modelFieldCase]", getModel().getModelFieldCase());
            fields = fields.replace("[fieldNameMin]", HandyManUtils.minStart(entity.getFields()[i].getName()));
            fields = fields.replace("[fieldNameMaj]", HandyManUtils.majStart(entity.getFields()[i].getName()));
        }
        content = content.replace("[fields]", fields);
        content = content.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
        content = content.replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
        content = content.replace("[classNameMaj]", HandyManUtils.majStart(entity.getClassName()));
        content = content.replace("[modelFieldCase]", getModel().getModelFieldCase());
        content = content.replace("[primaryFieldType]", entity.getPrimaryField().getType());
        content = content.replace("[primaryFieldNameMin]", HandyManUtils.minStart(entity.getPrimaryField().getName()));
        content = content.replace("[primaryFieldNameMaj]", HandyManUtils.majStart(entity.getPrimaryField().getName()));
        content = content.replace("[tableName]", entity.getTableName());
        return content;
    }

    // creation controlle
    public String generateController(Entity entity, Database database, Credentials credentials, String projectName) throws Throwable {
        String content = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + getController().getControllerTemplate() + "." + Constantes.CONTROLLER_TEMPLATE_EXT);
        content = content.replace("[namespace]", getSyntax().get("namespace"));
        content = content.replace("[namespaceStart]", getSyntax().get("namespaceStart"));
        content = content.replace("[namespaceEnd]", getSyntax().get("namespaceEnd"));
        content = content.replace("[package]", getController().getControllerPackage());
        String imports = "";
        for (String i : getController().getControllerImports()) {
            imports += i + "\n";
        }
        content = content.replace("[imports]", imports);
        String annotes = "";
        for (String a : getController().getControllerAnnotations()) {
            annotes += a + "\n";
        }
        content = content.replace("[controllerAnnotations]", annotes);
        content = content.replace("[extends]", getController().getControllerExtends());
        String fields = "", fieldAnnotes;
        for (ControllerField cf : getController().getControllerFields()) {
            fieldAnnotes = "";
            for (String a : cf.getControllerFieldAnnotations()) {
                fieldAnnotes += a + "\n";
            }
            fields += fieldAnnotes;
            fields += cf.getControllerFieldContent() + "\n";
        }
        // for(EntityField ef:entity.getFields()){
        //     if(ef.isForeign()&&getController().getControllerFieldsForeign()!=null){
        //         fieldAnnotes="";
        //         for(String a:getController().getControllerFieldsForeign().getControllerFieldAnnotations()){
        //             fieldAnnotes+=a+"\n";
        //         }
        //         fields+=fieldAnnotes;
        //         fields+=getController().getControllerFieldsForeign().getControllerFieldContent()+"\n";
        //         fields=fields.replace("[foreignNameMaj]", HandyManUtils.majStart(ef.getType()));
        //         fields=fields.replace("[foreignNameMin]", HandyManUtils.minStart(ef.getType()));
        //     }
        // }
        content = content.replace("[fields]", fields);
        String constructors = "";
        String constructorParameter, foreignInstanciation;
        for (String c : getController().getControllerConstructors()) {
            constructorParameter = "";
            foreignInstanciation = "";
            for (EntityField ef : entity.getFields()) {
                if (ef.isForeign()) {
                    constructorParameter = constructorParameter + "," + getController().getControllerForeignContextParam();
                    constructorParameter = constructorParameter.replace("[foreignNameMaj]", HandyManUtils.majStart(ef.getName()));
                    foreignInstanciation += getController().getControllerForeignContextInstanciation();
                    foreignInstanciation = foreignInstanciation.replace("[foreignNameMaj]", HandyManUtils.majStart(ef.getName())) + "\n";
                }
            }
            constructors += c + "\n";
            constructors = constructors.replace("[controllerForeignContextParam]", constructorParameter);
            constructors = constructors.replace("[controllerForeignContextInstanciation]", foreignInstanciation);
        }
        content = content.replace("[constructors]", constructors);
        String methods = "", methodAnnotes, methodParameters;
        String changeInstanciation, whereInstanciation, foreignList, foreignInclude;
        for (ControllerMethod m : getController().getControllerMethods()) {
            methodAnnotes = "";
            for (String ma : m.getControllerMethodAnnotations()) {
                methodAnnotes += ma + "\n";
            }
            methods += methodAnnotes;
            methodParameters = "";
            for (EntityField ef : entity.getFields()) {
                methodParameters += m.getControllerMethodParameter();
                if (!methodParameters.isEmpty()) {
                    methodParameters += ",";
                }
                methodParameters = methodParameters.replace("[fieldType]", ef.getType());
                methodParameters = methodParameters.replace("[fieldNameMin]", HandyManUtils.minStart(ef.getName()));
            }
            if (!methodParameters.isEmpty()) {
                methodParameters = methodParameters.substring(0, methodParameters.length() - 1);
            }
            methods += HandyManUtils.getFileContent(Constantes.DATA_PATH + "/" + m.getControllerMethodContent() + "." + Constantes.CONTROLLER_TEMPLATE_EXT);
            methods = methods.replace("[controllerMethodParameter]", methodParameters);
            changeInstanciation = "";
            foreignList = "";
            foreignInclude = "";
            for (int i = 0; i < entity.getFields().length; i++) {
                if (entity.getFields()[i].isPrimary()) {
                    continue;
                } else if (entity.getFields()[i].isForeign()) {
                    changeInstanciation += getController().getControllerForeignInstanciation().get("template");
                    changeInstanciation = changeInstanciation.replace("[content]", getTypeParsers().get(getTypes().get(database.getTypes().get(entity.getColumns()[i].getType()))));
                    changeInstanciation = changeInstanciation.replace("[value]", getController().getControllerForeignInstanciation().get("value"));
                    changeInstanciation = changeInstanciation.replace("[fieldNameMin]", HandyManUtils.minStart(entity.getFields()[i].getName()));
                    changeInstanciation = changeInstanciation.replace("[fieldNameMaj]", HandyManUtils.majStart(entity.getFields()[i].getName()));
                    changeInstanciation = changeInstanciation.replace("[foreignType]", entity.getFields()[i].getType());
                    changeInstanciation = changeInstanciation.replace("[referencedFieldNameMaj]", HandyManUtils.majStart(entity.getFields()[i].getReferencedField()));
                    changeInstanciation = changeInstanciation.replace("[foreignNameMin]", HandyManUtils.minStart(entity.getFields()[i].getName()));
                    foreignList += getController().getControllerForeignList();
                    foreignList = foreignList.replace("[foreignType]", entity.getFields()[i].getType());
                    foreignList = foreignList.replace("[foreignNameMin]", HandyManUtils.minStart(entity.getFields()[i].getName()));
                    foreignInclude += getController().getControllerForeignInclude();
                    foreignInclude = foreignInclude.replace("[foreignNameMaj]", HandyManUtils.majStart(entity.getFields()[i].getName()));
                    continue;
                }
                changeInstanciation += getController().getControllerChangeInstanciation().get("template");
                changeInstanciation = changeInstanciation.replace("[content]", getTypeParsers().get(entity.getFields()[i].getType()));
                changeInstanciation = changeInstanciation.replace("[value]", getController().getControllerChangeInstanciation().get("value"));
                changeInstanciation = changeInstanciation.replace("[fieldNameMin]", HandyManUtils.minStart(entity.getFields()[i].getName()));
                changeInstanciation = changeInstanciation.replace("[fieldNameMaj]", HandyManUtils.majStart(entity.getFields()[i].getName()));
            }
            whereInstanciation = "";
            whereInstanciation += getController().getControllerWhereInstanciation().get("template");
            whereInstanciation = whereInstanciation.replace("[content]", getTypeParsers().get(entity.getPrimaryField().getType()));
            whereInstanciation = whereInstanciation.replace("[value]", getController().getControllerWhereInstanciation().get("value"));
            methods = methods.replace("[primaryParse]", getTypeParsers().get(entity.getPrimaryField().getType()).replace("[value]", "[primaryNameMin]"));
            methods = methods.replace("[controllerChangeInstanciation]", changeInstanciation);
            methods = methods.replace("[controllerWhereInstanciation]", whereInstanciation);
            methods = methods.replace("[controllerForeignList]", foreignList);
            methods = methods.replace("[controllerForeignInclude]", foreignInclude);
            methods = methods.replace("[classNameMin]", HandyManUtils.minStart(entity.getClassName()));
            methods = methods.replace("[classNameMaj]", HandyManUtils.majStart(entity.getClassName()));
            methods = methods.replace("[primaryNameMaj]", HandyManUtils.majStart(entity.getPrimaryField().getName()));
            methods = methods.replace("[primaryType]", entity.getPrimaryField().getType());
            methods = methods.replace("[primaryNameMin]", HandyManUtils.minStart(entity.getPrimaryField().getName()));
            methods = methods.replace("[databaseDriver]", database.getDriver());
            methods = methods.replace("[databaseSgbd]", database.getNom());
            methods = methods.replace("[databaseHost]", credentials.getHost());
            methods = methods.replace("[databasePort]", database.getPort());
            methods = methods.replace("[databaseName]", credentials.getDatabaseName());
            methods = methods.replace("[user]", credentials.getUser());
            methods = methods.replace("[pwd]", credentials.getPwd());
            methods = methods.replace("[databaseUseSSL]", String.valueOf(credentials.isUseSSL()));
            methods = methods.replace("[databaseAllowKey]", String.valueOf(credentials.isAllowPublicKeyRetrieval()));
            methods = methods.replace("[typess]", HandyManUtils.majStart(entity.getPrimaryField().getType()));
        }
        content = content.replace("[methods]", methods);
        content = content.replace("[controllerNameMaj]", getController().getControllerName());
        content = content.replace("[classNameMaj]", HandyManUtils.majStart(entity.getClassName()));
        content = content.replace("[classNameMin]", HandyManUtils.minStart(entity.getClassName()));
        content = content.replace("[projectNameMin]", HandyManUtils.minStart(projectName));
        content = content.replace("[projectNameMaj]", HandyManUtils.majStart(projectName));
        content = content.replace("[databaseDriver]", database.getDriver());
        content = content.replace("[databaseSgbd]", database.getNom());
        content = content.replace("[databaseHost]", credentials.getHost());
        content = content.replace("[databaseName]", credentials.getDatabaseName());
        content = content.replace("[databasePort]", database.getPort());
        content = content.replace("[databaseID]", String.valueOf(database.getId()));
        content = content.replace("[user]", credentials.getUser());
        content = content.replace("[pwd]", credentials.getPwd());
        content = content.replace("[databaseUseSSL]", String.valueOf(credentials.isUseSSL()));
        content = content.replace("[databaseAllowKey]", String.valueOf(credentials.isAllowPublicKeyRetrieval()));
        return content;
    }

    // creation view
    public void generateAComponent(Entity entity, String componentLocation, String urlService) throws Throwable {
        generateAngularModel(entity, componentLocation);
        generateAngularService(entity, componentLocation, urlService);
        generateAngularComponent(entity, componentLocation);
        generateSpecANgular(entity, componentLocation);
        generateAngularHtml(entity, componentLocation);
    }

    public void overWriteRoute(Entity[] entities, String filepath) throws Throwable {
        String content = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/angular/routeAngular." + Constantes.MODEL_TEMPLATE_EXT);
        String imports = "";
        String routes = "";
        for (int i = 0; i < entities.length; i++) {
            imports += "import { " + HandyManUtils.majStart(entities[i].getClassName()) + "Component } from './" + HandyManUtils.minStart(entities[i].getClassName()) + "/" + HandyManUtils.minStart(entities[i].getClassName()) + ".component'; \n";
            routes += "{path : '" + HandyManUtils.minStart(entities[i].getClassName()) + "',component : " + HandyManUtils.majStart(entities[i].getClassName()) + "Component },\n \t";
        }
        content = content.replace("[imports]", imports);
        content = content.replace("[routes]", routes);
        HandyManUtils.overwriteFileContent(filepath + "/app.routes.ts", content);
    }

    public void overWriteModule(Entity[] entities, String filepath) throws Throwable {
        String content = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/angular/ModuleAngular." + Constantes.MODEL_TEMPLATE_EXT);
        String imports = "";
        String routes = "";
        for (int i = 0; i < entities.length; i++) {
            imports += "import { " + HandyManUtils.majStart(entities[i].getClassName()) + "Component } from './" + HandyManUtils.minStart(entities[i].getClassName()) + "/" + HandyManUtils.minStart(entities[i].getClassName()) + ".component'; \n";
            routes += HandyManUtils.majStart(entities[i].getClassName()) + "Component , \n \t";
        }
        content = content.replace("[imports]", imports);
        content = content.replace("[classComponents]", routes);
        HandyManUtils.overwriteFileContent(filepath + "/app.module.ts", content);
    }

    public void generateSpecANgular(Entity entity, String path) throws Throwable {
        String content = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/angular/SpecAngular." + Constantes.MODEL_TEMPLATE_EXT);
        content = content.replace("[classNameMaj]", HandyManUtils.majStart(entity.getClassName()));
        content = content.replace("[classNameMin]", HandyManUtils.minStart(entity.getClassName()));
        String patht = path + "/" + HandyManUtils.minStart(entity.getClassName()) + "/" + HandyManUtils.minStart(entity.getClassName()) + ".spec.ts";
        HandyManUtils.createFile(patht);
        HandyManUtils.overwriteFileContent(patht, content);
    }

    public void generateAngularModel(Entity entity, String projectFilePath) throws Throwable {
        String content = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/angular/ModelAngular." + Constantes.MODEL_TEMPLATE_EXT);
        content = content.replace("[classNameMaj]", HandyManUtils.majStart(entity.getClassName()));
        String fields = "";
        String importation = "";
        for (int i = 0; i < entity.getFields().length; i++) {
            // if(entity.getFields()[i].isForeign()){
            //     importation+="import {"+HandyManUtils.majStart(entity.getFields()[i].getName())+"} from ../"+HandyManUtils.minStart(entity.getFields()[i].getName())+"/"+HandyManUtils.minStart(entity.getFields()[i].getName())+".model;";
            //     fields+=HandyManUtils.minStart(entity.getFields()[i].getName())+" : "+entity.getFields()[i].getType()+" ; \n \t";
            // }

            fields += HandyManUtils.minStart(entity.getFields()[i].getName()) + " : " + entity.getFields()[i].getAngularType() + " ; \n \t";

        }
        content = content.replace("[field]", fields);
        content = content.replace("[import]", importation);
        String path = projectFilePath + "/" + HandyManUtils.minStart(entity.getClassName()) + "/" + HandyManUtils.minStart(entity.getClassName()) + ".model.ts";
        HandyManUtils.createFile(path);
        HandyManUtils.overwriteFileContent(path, content);
    }

    public void generateAngularService(Entity entity, String projectFilePath, String url) throws Throwable {
        String content = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/angular/ServiceAngular." + Constantes.MODEL_TEMPLATE_EXT);
        content = content.replace("[classNameMaj]", HandyManUtils.majStart(entity.getClassName()));
        content = content.replace("[url]", url);
        content = content.replace("[classNameMin]", HandyManUtils.minStart(entity.getClassName()));
        String path = projectFilePath + "/" + HandyManUtils.minStart(entity.getClassName()) + "/" + HandyManUtils.minStart(entity.getClassName()) + ".service.ts";
        HandyManUtils.createFile(path);
        HandyManUtils.overwriteFileContent(path, content);
    }

    public void generateAngularComponent(Entity entity, String projetctFilePathl) throws Throwable {
        String content = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/angular/ComponentAngular." + Constantes.MODEL_TEMPLATE_EXT);
        content = content.replace("[classNameMaj]", HandyManUtils.majStart(entity.getClassName()));
        content = content.replace("[classNameMin]", HandyManUtils.minStart(entity.getClassName()));
        String path = projetctFilePathl + "/" + HandyManUtils.minStart(entity.getClassName()) + "/" + HandyManUtils.minStart(entity.getClassName()) + ".component.ts";
        HandyManUtils.createFile(path);
        HandyManUtils.overwriteFileContent(path, content);
    }

    public void generateAngularHtml(Entity entity, String projectFilePath) throws Throwable {
        String content = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/angular/HtmlAngular." + Constantes.MODEL_TEMPLATE_EXT);
        String fields = "";
        String valeurs = "";
        String inputs = "";
        String updateInput = "";
        String primary = "";
        String pagination = "";
        for (int i = 0; i < entity.getFields().length; i++) {
            fields += "<th>" + HandyManUtils.minStart(entity.getFields()[i].getName()) + " </th> \n \t";
            valeurs += "<td>{{uneligne." + HandyManUtils.minStart(entity.getFields()[i].getName()) + "}}</td> \n \t";
            if (!entity.getFields()[i].isPrimary()) {
                inputs += "<div class=\"row mb-3 mt-2\"><label class=\"col-form-label offset-1 h3\">" + HandyManUtils.majStart(entity.getFields()[i].getName()) + "</label> \n";
                inputs += "<div class=\"offset-1 col-sm-10\" ><input type='" + entity.getFields()[i].getAngularType() + "' class='form-control' [(ngModel)]='nouvel[classNameMaj]." + entity.getFields()[i].getName() + "' name='" + entity.getFields()[i].getName() + "'></div></div> \n";
                updateInput += "<label>" + HandyManUtils.majStart(entity.getFields()[i].getName()) + "</label> \n";
                updateInput += "<input type='" + entity.getFields()[i].getAngularType() + "' class='form-control' [(ngModel)]='[classNameMin]Selectionne." + entity.getFields()[i].getName() + "' name='" + entity.getFields()[i].getName() + "'> \n";
            } else {
                primary = entity.getFields()[i].getName();
                updateInput += "<input type='hidden' [(ngModel)]='[classNameMin]Selectionne." + entity.getFields()[i].getName() + "' name='" + entity.getFields()[i].getName() + "'> \n";
            }
        }
        pagination += "<button (submit)=(pagination(uneligne." + HandyManUtils.minStart(entity.getPrimaryField().getName()) + "))>Suivant</button>";

        content = content.replace("[attributs]", fields);
        content = content.replace("[pagination]", pagination);
        content = content.replace("[valeurs]", valeurs);
        content = content.replace("[primary]", primary);
        content = content.replace("[inputs]", inputs);
        content = content.replace("[updates]", updateInput);
        content = content.replace("[classNameMaj]", HandyManUtils.majStart(entity.getClassName()));
        content = content.replace("[classNameMin]", HandyManUtils.minStart(entity.getClassName()));
        String path = projectFilePath + "/" + HandyManUtils.minStart(entity.getClassName()) + "/" + HandyManUtils.minStart(entity.getClassName()) + ".html";
        HandyManUtils.createFile(path);
        HandyManUtils.overwriteFileContent(path, content);
    }

    public void generateMenu(Entity[] entities, String filepath) throws Throwable {
        String contentbase = HandyManUtils.getFileContent(Constantes.DATA_PATH + "/angular/MenuAngular." + Constantes.MODEL_TEMPLATE_EXT);
        String content = "";
        for (int i = 0; i < entities.length; i++) {
            content += "<li class=\"nav-item\">\n<a class=\"nav-link collapsed\" href = '/" + HandyManUtils.minStart(entities[i].getClassName()) + "'>\n<span>" + HandyManUtils.minStart(entities[i].getClassName()) + "</span>\n</a>\n</li>\n \t";
        }
        contentbase = contentbase.replace("[listes]", content);
        HandyManUtils.overwriteFileContent(filepath + "/app.component.html", contentbase);
    }
}

