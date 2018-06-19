class Main {
    public static void main(String[] args) {
        Links html = new Links.HTML(args[0], new HTTP.Default());
        System.out.println(html.toString());
    }
}
