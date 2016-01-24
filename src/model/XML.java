package model;

public class XML {
    Data data;

    public XML() {
        this.data = new Data();
    }

    public XML(Data data) {
        this.data = data;
    }

    public Data extract() {
        return this.data;
    }

    public boolean save() {
        return false;
    }
}
