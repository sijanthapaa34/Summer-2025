public interface Resizable {
    public void resizeWidth(int width);
    public void resizeHeight(int height);
    public void resize(int width, int height, boolean maintainAspectRatio);
}
