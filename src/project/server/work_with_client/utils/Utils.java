package project.server.work_with_client.utils;

import project.server.work_with_client.SessionWithClient;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by rikachka on 07.11.15.
 */

// TODO: большая часть утилит есть в стандартных классах
public class Utils {
    public static boolean checkUserAuthorised(SessionWithClient session) {
        if (session.getUser() == null) {
            session.writeErrorToClient("you are not authorised");
            return false;
        }
        return true;
    }

    public static boolean checkUserNotAuthorised(SessionWithClient session) {
        if (session.getUser() != null) {
            session.writeErrorToClient("you are already authorised");
            return false;
        }
        return true;
    }

    public static boolean checkChatExisting(SessionWithClient session, Long chatId) {
        if (session.chats().getChat(chatId) == null) {
            session.writeErrorToClient("no chat " + chatId);
            return false;
        }
        return true;
    }

    public static boolean checkUserExisting(SessionWithClient session, Long userId) {
        if (session.users().getUser(userId) == null) {
            session.writeErrorToClient("no user " + userId);
            return false;
        }
        return true;
    }

    public static boolean checkUserParticipating(SessionWithClient session, Long chatId) {
        if (!session.chats().getChat(chatId).getParticipants().contains(session.getUser().getId())) {
            session.writeErrorToClient("you do not participate chat " + chatId);
            return false;
        }
        return true;
    }


    public static String currentPath() {
        return System.getProperty("user.dir");
    }

    public static Path makePathAbsolute(String str) {
        Path path = Paths.get(str);
        if (!path.isAbsolute()) {
            path = Paths.get(Utils.currentPath(), path.toString());
        }
        return path.normalize();
    }

    public static Path makePathAbsolute(Path mainPath, String str) {
        Path path = Paths.get(str);
        if (!path.isAbsolute()) {
            path = Paths.get(mainPath.toString(), path.toString());
        }
        return path.normalize();
    }

    public static Path makePathAbsolute(File mainPath, String str) {
        Path path = Paths.get(str);
        if (!path.isAbsolute()) {
            path = Paths.get(mainPath.toString(), path.toString());
        }
        return path.normalize();
    }

    public static Path toPath(String str) {
        return Paths.get(str).toFile().toPath();
    }

    public static File toFile(String str) {
        return Paths.get(str).toFile();
    }


    public static void delete(File file) throws Exception {
        if (!file.delete()) {
            throw new Exception(file.toString() + ": can't delete");
        }
    }

    public static void createDir(File dir) throws Exception {
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new Exception("incorrect name: can't create directory " + dir);
            }
        }
    }

    public static void createFile(File file) throws Exception {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                throw new Exception("error while creating file " + file);
            }
        }
    }
}
