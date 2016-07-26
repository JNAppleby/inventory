package errcodes;

/**
 * Error handing enum
 * @author ymudryy
 *
 */
public enum ErrCode {
  ADD_SUCCESS(ErrLevel.SUCCESS, "Successfully added an item."),
  ADD_DUPLICATE_ITEM(ErrLevel.ERROR, "Cannot add items with duplicate name."),
  ADD_NULL_ITEM(ErrLevel.ERROR, "Cannot add null item."),
  ADD_NAME_MIN_LEN(ErrLevel.ERROR, "Item's name minimum length constraint is not met."),

  REMOVE_SUCCESS(ErrLevel.SUCCESS, "Item successfully removed."),
  REMOVE_NON_EXISTENT(ErrLevel.ERROR, "Cannot remove a non-existing item.");

  private final ErrLevel level;
  private final String description;

  private ErrCode(ErrLevel level, String description) {
    this.level = level;
    this.description = description;
  }

  public String getDescription() {
     return description;
  }

  public ErrLevel getLevel() {
      return level;
  }

  public String getLevelString() {
      return level.toString();
  }

  @Override
  public String toString() {
    return description;
  }
}