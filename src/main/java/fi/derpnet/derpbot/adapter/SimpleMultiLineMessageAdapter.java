package fi.derpnet.derpbot.adapter;

import fi.derpnet.derpbot.bean.RawMessage;
import fi.derpnet.derpbot.connector.IrcConnector;
import fi.derpnet.derpbot.controller.MainController;
import fi.derpnet.derpbot.handler.RawMessageHandler;
import fi.derpnet.derpbot.handler.SimpleMultiLineMessageHandler;
import fi.derpnet.derpbot.util.RawMessageUtils;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Adapter for using a SimpleMultiLineMessageHandler in place of a
 * RawMessageHandler
 */
public class SimpleMultiLineMessageAdapter implements RawMessageHandler {

    private final SimpleMultiLineMessageHandler handler;
    private final BiFunction<RawMessage, IrcConnector, List<RawMessage>> handle;

    public SimpleMultiLineMessageAdapter(SimpleMultiLineMessageHandler handler) {
        this.handler = handler;
        handle = handler.isLoud() ? this::handleLoud : this::handleNormal;
    }

    @Override
    public List<RawMessage> handle(RawMessage message, IrcConnector ircConnector) {
        return handle.apply(message, ircConnector);
    }

    private List<RawMessage> handleNormal(RawMessage message, IrcConnector ircConnector) {
        if (message.command.equals("PRIVMSG")) {
            String incomingRecipient = message.parameters.get(0);
            String messageBody = message.parameters.get(1);
            List<String> responseBodies = handler.handle(message.prefix, incomingRecipient, messageBody, ircConnector);
            if (responseBodies == null) {
                return null;
            }
            String responseRecipient = RawMessageUtils.getRecipientForResponse(message);
            return responseBodies.stream().map(msg -> new RawMessage(null, "PRIVMSG", responseRecipient, ':' + msg)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    private List<RawMessage> handleLoud(RawMessage message, IrcConnector ircConnector) {
        if (message.command.equals("PRIVMSG")) {
            String incomingRecipient = message.parameters.get(0);
            String messageBody = message.parameters.get(1);
            List<String> responseBodies = handler.handle(message.prefix, incomingRecipient, messageBody, ircConnector);
            if (responseBodies == null) {
                return null;
            }
            String responseRecipient = RawMessageUtils.getRecipientForResponse(message);
            if (ircConnector.getQuieterChannels() != null && ircConnector.getQuieterChannels().contains(responseRecipient.toLowerCase())) {
                responseRecipient = RawMessageUtils.privMsgSender(message);
            }
            String r = responseRecipient; // local variables referenced from a lambda expression must be final or effectively final
            return responseBodies.stream().map(msg -> new RawMessage(null, "PRIVMSG", r, ':' + msg)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public void init(MainController controller) {
        handler.init(controller);
    }

    @Override
    public String getCommand() {
        return handler.getCommand();
    }

    @Override
    public String getHelp() {
        return handler.getHelp();
    }

}
