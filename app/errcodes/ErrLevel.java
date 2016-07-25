package errcodes;

/**
 * Error level description enum
 *
 * @author ymudryy
 *
 */
public enum ErrLevel {
    SUCCESS(0, "success"), INFO(1, "info"), WARNING(2, "warning"), ERROR(3, "danger");

    private final int levelCode;
    private final String levelString;

    private ErrLevel(int levelCode, String levelString) {
        this.levelCode = levelCode;
        this.levelString = levelString;
    }

    public int getLevelCode() {
        return this.levelCode;
    }

    @Override
    public String toString() {
        return this.levelString;
    }
}
