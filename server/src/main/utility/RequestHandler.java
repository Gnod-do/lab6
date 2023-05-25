package main.utility;


import main.interaction.Request;
import main.interaction.Response;
import main.interaction.ResponseCode;

/**
 * Handles requests.
 */

public class RequestHandler {
    private CommandManager commandManager;

    public RequestHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    /**
     * Handles requests.
     *
     * @param request Request to be processed.
     * @return Response to request.
     */

    public Response handle(Request request) {
        commandManager.addToHistory(request.getCommandName());
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        return new Response(responseCode, ResponseOutputer.getAndClear());
    }

    /**
     * Executes a command from a request.
     *
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */

    private ResponseCode executeCommand(String command, String commandStringArgument,
                                        Object commandObjectArgument) {
        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "info":
                if (!commandManager.info(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "show":
                if (!commandManager.show(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "add":
                if (!commandManager.add(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "update":
                if (!commandManager.update(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
//            case "save":
//                if (!commandManager.save(commandStringArgument, commandObjectArgument))
//                    return ResponseCode.ERROR;
//                break;
            case "execute_script":
                if (!commandManager.executeScript(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "remove_at_index":
                if (!commandManager.removeAtIndex(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "sort":
                if (!commandManager.sort(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "sum_of_transferred_students":
                if (!commandManager.sumOfTransferredStudents(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "min_by_semester_enum":
                if (!commandManager.minBySemesterEnum(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "group_counting_by_coordinates":
                if (!commandManager.groupCountingByCoordinates(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "history":
                if (!commandManager.history(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                break;
            case "server_exit":
                if (!commandManager.serverExit(commandStringArgument, commandObjectArgument))
                    return ResponseCode.ERROR;
                return ResponseCode.SERVER_EXIT;
            default:
                ResponseOutputer.appendln("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;
    }
}
