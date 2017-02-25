package util;

public enum CategoryType {
    ALL("ALL"),
    EDU("EDU"),
    INF("INF"),
    JOB("JOB"),
    SHO("SHO");

    private final String text;

    private CategoryType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
