package ServerClient;

import java.io.IOException;

public interface Command {
    void execute(String[] parts) throws IOException;
}
