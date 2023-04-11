package designPattern;

import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

public class C11_Composite {
    /**
     *  Composite 패턴은 그릇과 내용물을 동일시하여 재귀적인 구조를 만드는 디자인 패턴이 Composite이다.
     *
     *
     *  Entry : 추상 클래스
     */
    public abstract class Entry {
        public abstract String getName();
        public abstract int getSize();
        public void printList(){
            printList("");
        }
        protected abstract void printList( String prefix );

        @Override
        public String toString() {
            return getName() + " ("+getSize()+") ";
        }
    }
    public class File extends Entry {
        private String name;
        private int size;

        public File(String name, int size){
            this.name = name;
            this.size = size;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getSize() {
            return size;
        }

        @Override
        protected void printList(String prefix) {
            System.out.println(prefix +"/"+this);
        }
    }
    public class Directory extends Entry {
        private String name;
        private List<Entry> directory = new ArrayList<>();

        public Directory(String name){
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getSize() {
            return directory.stream().map(Entry::getSize).reduce((integer, integer2) -> integer + integer2).orElseGet(() -> 0);
        }

        @Override
        protected void printList(String prefix) {
            System.out.println(prefix+"/"+this);
            directory.stream().forEach(v -> {
                v.printList(prefix+"/"+name);
            });
        }

        public Entry add(Entry entry){
            directory.add(entry);
            return this;
        }
    }

    @Test
    void compositeTest() {
        System.out.println("MAKE ROOT ENTRIES");
        Directory rootDir = new Directory("root");
        Directory binDir = new Directory("bin");
        Directory tmpDir = new Directory("tmp");
        Directory usrDir = new Directory("usr");

        rootDir.add(binDir);
        rootDir.add(tmpDir);
        rootDir.add(usrDir);

        binDir.add(new File("vi", 10000));
        binDir.add(new File("latex", 20000));

        rootDir.printList();
        System.out.println();

        System.out.println("MAKE USER ENTRIES");
        Directory newkayak = new Directory("NEWKAYAK");
        Directory sanghyeon = new Directory("sanghyeon");
        Directory lzyjin = new Directory("lzyjin");

        usrDir.add(newkayak);
        usrDir.add(sanghyeon);
        usrDir.add(lzyjin);

        newkayak.add(new File("DIRARY.html", 100));
        newkayak.add(new File("Composite.java", 200));

        sanghyeon.add(new File("memo.txt", 300));

        lzyjin.add(new File("DOCUMENTS.doc", 400));
        lzyjin.add(new File("index.html", 500));
        rootDir.printList();


    }

    /**
     * Leaf : 내용물 이 안에 다른 것을 넣을 수 없다.
     * Composite: 그릇을 나타내며 Leaf, Composite를 넣을 수 있다.
     * Component : Leaf와 Composite를 동일시하기 위한 역할 (위 예시에서 Entry)
     *
     *
     *
     *
     *
     *         Composite는 복, 단수를 동일시로 부를 수 있다.
     */
}
