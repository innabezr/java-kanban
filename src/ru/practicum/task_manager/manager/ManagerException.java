package ru.practicum.task_manager.manager;

public class ManagerException extends RuntimeException {
    private static final String ERR_save = "Error while saving";
    private static final String ERR_load = "Error while loading";

    public static ManagerException saveException(Exception e) {
        return new ManagerException(ERR_save, e);
    }

    public static ManagerException loadException(Exception e) {
        return new ManagerException(ERR_load, e);
    }

    private ManagerException(String ERR, Exception e) {
        super(ERR, e);
    }
}
