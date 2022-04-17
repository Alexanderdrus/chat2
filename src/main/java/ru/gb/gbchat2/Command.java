package ru.gb.gbchat2;

public enum Command {

    AUTH("/auth"), //          /auth login1 pass1
    AUTHOK("/authok"), //      /authok nick1
    PRIVATE_MASSAGE("/w"), //  /w nick1 Текст сообщения для пользователя
    END("/end");  //           /end

    private String command;
    private String[] params=new String[0];

    Command(String command) {
        this.command = command;
    }


    private static boolean isCommand(String message) {
        return message.startsWith("/");

    }

    public String[] getParams() {
        return params;
    }

    public String getCommand() {
        return command;
    }

    private static Command getCommand(String message){
        message=message.trim();
        if(!isCommand(message)){
            throw new RuntimeException("'" + message +"' is not a command");
        }
        final int index = message.indexOf(" ");
        final String cmd = index > 0 ? message.substring(0, index) : message;

        for (Command value : Command.values()) {
            if (value.getCommand().equals(cmd)){
                return value;
            }
        }
        return null;

    }

}
