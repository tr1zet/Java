package lsp;

public class Penguin extends NonFlyingBird {
    @Override
    public void eat() {
        System.out.println("<звуки пингвина>, ем рыбу, <еще звуки пингвина>");
    }

    public void swim() {
        System.out.println("Пингвин плавает в воде");
    }
}
