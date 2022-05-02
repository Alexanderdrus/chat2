package ru.gb.gbchat2;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Command {

    AUTH("/auth"){
        @Override
        public String[] parse(String commandText) {  //          /auth login1 pass1
            final String[] split = commandText.split(COMMAND_DELIMETR,3);
            return new String[]{split[1],split[2]};
        }
    },
    AUTHOK("/authok"){
        @Override
        public String[] parse(String commandText) {  //      /authok nick1
            return new String[]{commandText.split(COMMAND_DELIMETR)[1]};
        }
    },

    PRIVATE_MESSAGE("/w"){
        @Override
        public String[] parse(String commandText) {  //  /w nick1 Текст сообщения для пользователя
            final String[] split = commandText.split(COMMAND_DELIMETR);
            final String nick = split[1];
            final String msg = split[2];
            return new String[]{nick,msg};
        }
    },

    END("/end"){
        @Override
        public String[] parse(String commandText) {
            return new String[0];
        }
    },//           /end

    ERROR("/error"){ // /error Сообщение об ошибке
        @Override
        public String[] parse(String commandText) {
            final String errorMsg = commandText.split(COMMAND_DELIMETR, 2)[1];
            return new String[]{errorMsg};
        }
    };
    private static final Map<String,Command> map = Stream.of(Command.values())
            .collect(Collectors.toMap(Command::getCommand, Function.identity()));

    private String command;
    private String[] params=new String[0];
    static final String COMMAND_DELIMETR = "\\s+";


    Command(String command) {
        this.command = command;
    }

    public static boolean isCommand(String message) {
        return message.startsWith("/");

    }

    public String[] getParams() {
        return params;
    }

    public String getCommand() {
        return command;
    }

    public static Command getCommand(String message){
        message=message.trim();
        if(!isCommand(message)){
            throw new RuntimeException("'" + message +"' is not a command");
        }
        final int index = message.indexOf(" ");
        final String cmd = index > 0 ? message.substring(0, index) : message;

        return map.get(cmd);

    }

    public abstract String[] parse(String commandText);

    public String collectMessage(String... params) {
        final String command = this.getCommand();
        return command +
                (params == null
                        ? ""
                        : " " + String.join(" ", params)) ;
    }
}
