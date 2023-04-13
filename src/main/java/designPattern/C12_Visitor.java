package designPattern;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 2023-04-12
 * Project 2023JavaStudy
 */
public class C12_Visitor {
    /**
     * 데이터 구조 안에 많은 요소가 저장되어 있고, 각 요소에 대해서 어떠한 처리를 한다고 가정하자. 이때 그 '처리' 코드는 어디에 있어야 할까?
     * 일반적으로 생각하면 데이터 구조를 나타내는 클래스 안에 쓸 것이다. 하지만 그 '처리'가 한 종류가 아니라면?
     * 이런 경우에는 새로운 처리가 필요할 때마다 데이터 구조의 클래스를 수정해야한다.
     *
     * Vistor 패턴에서는 데이터 구조와 처리를 분리한다. 데이터 구조 안을 돌아다니는 주체인 '방문자'를 나타내는 클래스를 준비하고 그 클래스에
     * 처리를 맡긴다. 새로운 처리를 추가하고 싶을 때는 새로운 '방문자'를 만들면 된다.
     */

    //Visitor : 방문자를 나타내는 추상 클래스, 이 방문자느 방문하는 곳의 데이터 구조에 의존한다.
    public abstract class Visitor {
        public abstract void visit(File file);
        public abstract void visit(Directory directory);
    }
    public interface Element {
        public void accept(Visitor visitor);
    }
    public abstract class Entry implements Element {
        public abstract String getName();
        public abstract int getSize();

        @Override
        public String toString() {
            return getName() + "("+getSize()+")";
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
        public void accept(Visitor visitor) {
                visitor.visit(this);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getSize() {
            return size;
        }
    }
    public class Directory extends Entry implements Iterable<Entry>{
        private String name;
        private List<Entry> directory = new ArrayList<>();

        public Directory(String name) {
            this.name = name;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public int getSize() {
            return directory.stream().map(Entry::getSize).reduce((integer, integer2) -> integer + integer2).orElseGet(() -> 0);
        }

        public Entry add(Entry entry) {
            directory.add(entry);
            return this;
        }
        @Override
        public Iterator<Entry> iterator() {
            return directory.iterator();
        }
    }
    //ListVisitor는 visitor의 하위 클래스로, 데이터 구조를 돌아다니면서 목록을 표시한다. Visitor 클래스의 하위 클래스이다.
    //Visitor의 visit을 오버라이드한 메소드는 해당 인스턴스에 해야할 처리를 기술하는 장소이다.
    public class ListVisitor extends Visitor {
        private String currentDir = "";

        @Override
        public void visit(File file) {
            System.out.println(currentDir+"/"+file);
        }

        @Override
        public void visit(Directory directory) {
            System.out.println(currentDir+"/"+directory);
            String saveDir = currentDir;
            currentDir = currentDir+"/"+directory.getName();
            for (Entry entry : directory) {
                entry.accept(this);
            }
            currentDir = saveDir;
        }
    }

    @Test
    void visitorTest(){
        System.out.println("MAKE ROOT");
        Directory rootDir = new Directory("root");
        Directory binDir = new Directory("bin");
        Directory tmpDir = new Directory("tmp");
        Directory usrDir = new Directory("usr");
        rootDir.add(binDir);
        rootDir.add(tmpDir);
        rootDir.add(usrDir);

        binDir.add(new File("VI", 10000));
        binDir.add(new File("LATEX", 20000));
        rootDir.accept(new ListVisitor());
        System.out.println();


        System.out.println("MAKING USER ENTRIES");
        Directory a = new Directory("A");
        Directory b = new Directory("B");
        Directory c = new Directory("C");
        usrDir.add(a);
        usrDir.add(b);
        usrDir.add(c);
        a.add(new File("DIARY.html", 100));
        a.add(new File("Visitor.java", 200));

        b.add(new File("memo.txt", 211));

        c.add(new File("Hello.swift", 200));
        c.add(new File("HELLO.playground", 200));
        rootDir.accept(new ListVisitor());


    }

    /**
     * 흐름
     *
     * 1. test 메소드가 ListVisitor 인스턴스 만듦
     * 2. Directory에 대해서 accept 호출
     * 3. Driectory 인스턴스는 인수로 전달된 ListVisitor의 visit(Directory)를 호출
     * 4. ListVisitor의 인스턴스는 디렉토리를 살펴보고 accept호출
     * 5. File은 ListVisitor의 visit을 호출
     * ...
     *
     *
     * 결과적으로 보면 ListVisitor에 visit에 대해서 처리가 집중됨을 알 수 있다.
     *
     *  Visitor : 데이터 구조의 구체적 요소마다 visit()을 선언한다.
     *  ConcreteVisitor : Visitor의 인터페이스를 구현, visit()을 구현한다. 각 ConcreteElement 마다 처리를 기술한다. (예시에서는 ListVisitor)
     *  Element: Visitor가 방문할 곳을 나타내며, 방문자를 받아들이는 accept를 선언
     *  ConcreteElement: Element 인터페이스를 구현한다.
     *  ObjectStructure: Element 집합을 다룬다. ConcreteVisitor가 각각 Element를 취급할 수 있는 메소드를 갖추고 있다.
     *
     *
     *
     *              더블 디스패치
     *  Visitor 패턴에서 사용되는 메소드 호출을 정리해보자.
     *      accept는
     *  element.accept(visitor)
     *
     *      visit은
     *  visitor.visit(element)
     *
     *  둘을 비교하면 반대에 있다. Visitor 패턴에서는 ConcreteElement, ConcreteVisitor의 조합으로 실제 처리를 결정한다. 이를 일반적으로
     *  더블 디스패치(doubleDispatch)라고 한다.
     *
     *
     *
     *              Visitor의 목적
     *  일단 흐름이 복잡해지는게 문제다. accept, visit!
     *  Visitor의 큰 목적은 처리를 데이터 구조와 분리하는 것이다. 데이터 구조는 요소를 집합으로 정리하거나 요소 사이를 연결해주는 중요한 역할을 한다.
     *  그러나, 구조를 유지하는 것과 그 구조를 기초로한 처리를 기술하는 것과는 다르다.
     *
     *  Visitor 패턴은 처리를 분리했기 때문에 Directory 등과 같은 ObjectStructure의 부품으로서의 독립성을 높여준다.
     *
     *
     *
     *              The Open-Closed Principle
     *  확장에는 열려있고 수정에는 닫는다. Visitor는 처리와 데이터 구조를 나눔으로써 수정 없이 확장할 수 있도록 해준다.
     *
     *  1. ConcreteVisitor 역할의 추가는 쉽다.
     *  구체적인 처리를 ConcreteVisitor에게 맡기게 되는데, 처리를 위해서 굳이 수정하지 않고 새로 추가하면 된다.
     *
     *  2. ConcreteElement 추가는 어렵다.
     *
     *
     *
     *
     */
}
