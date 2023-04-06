package designPattern;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created on 2023-04-06
 * Project 2023JavaStudy
 */
public class C01_Iterator {
    class Book {
        private String name;

        public Book(String name) {
            this.name = name;
        }
        public String getName(){ return this.name; }
    }
    class BookShelf implements Iterable<Book> {
//        private Book[] books;
        private List<Book> books;
        private int last = 0;

//        public BookShelf ( int maxSize ) {
//            this.books = new Book[maxSize];
//        }
        public BookShelf () {
            this.books = new ArrayList<>();
        }

        public Book getBookAt ( int index ) {
//            return this.books[ index ];
            return this.books.get( index );
        }
        public void appendBook ( Book book ) {
//            this.books[last++] = book;
            this.books.add(last++,  book);
        }
        public int getLength () {
            return last;
        }

        @Override
        public Iterator<Book> iterator() {
            return new BookShelfIterator(this);
            /**
             * BookShelf 클래스에 대응하는 Iterator로서 BookShelfIterator 인스턴스 생성하고 반환
             */
        }
    }
    class BookShelfIterator implements Iterator<Book> {
        private BookShelf bookShelf;
        private int index;

        public BookShelfIterator( BookShelf bookShelf ) {
            this.bookShelf = bookShelf;
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < bookShelf.getLength();
        }

        @Override
        public Book next() {
            if(!hasNext()) throw new NoSuchElementException();
            return bookShelf.getBookAt(index++);
        }
    }


    @Test
    void iteratorTest() {
//        BookShelf bookShelf = new BookShelf(4);
        BookShelf bookShelf = new BookShelf();
        bookShelf.appendBook(new Book("Around the World in 80 Days"));
        bookShelf.appendBook(new Book("Cinderella"));
        bookShelf.appendBook(new Book("Load of Rings"));
        bookShelf.appendBook(new Book("Harry Potter"));

        Iterator<Book> bookIterator = bookShelf.iterator();

        while( bookIterator.hasNext() ){ //explicit
            Book book = bookIterator.next();
            System.out.println( book.getName() );
        }

        System.out.println();

        for ( Book book : bookShelf ) { //implicit
            System.out.println(book.getName());
        }
        System.out.println();

        /**
         * For in 문에서도 내부적으로 Iterator를 사용하고 있다.
         */


        /**
         * Iterator 패턴의 요소
         *
         *  Iterator : 요소를 순서대로 검색하는 인터페이스를 결정한다.
         *  ConcreteIterator: Iterator가 결정한 인터페이스를 실제로 구현한다. (위의 예시에서는 BookShelfIterator)
         *
         *  Aggregate : Iterator를 만들어내는 인터페이스를 결정
         *  ConcreteAggregate : Aggregate가 결정한 인터페이스를 실제로 구현 (위의 예시에서는 BookShelf)
         *
         *
         *
         *
         *   >> 왜 Iterator를 구현하는 번거로운 짓을 하는가? for라는 좋은 수단이 있다.
         *  가장 큰 이유는 Iterator로 구현과 분리하여 반복할 수 있기 때문이다.
         *
         *              while( bookIterator.hasNext() ){
         *                Book book = bookIterator.next();
         *                System.out.println( book.getName() );
         *              }
         *
         *  여기에서 사용한 것은 hasNext, next 라는 Iterator의 메소드다. BookShelf 구현에 사용된 메소드는 호출되지 않는다.
         *  즉, while은 BookShelf와 의존적이지 않다.
         *
         *  만약 ArrayList로 관리한다고 해도, BookShelf가 iterator 메소드를 가지고 있고 올바른 Iterator를 반환하면 while을 변경하지 않아도
         *  동작할 것이다.
         *
         *  이는 한 부분을 수정해도 다른 부분을 연달아 수정하지 않아도 된다는 의미가 된다.
         *
         */
    }
}
