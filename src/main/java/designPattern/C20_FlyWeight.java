package designPattern;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
     * Flyweight는 '인스턴스를 최대한 공유하고 쓸데없이 new 하지 않는다.'로 요약할 수 있다.
     *
     * 아래에서 만들 Bigchar은 큰 문자를 표현하는 클래스이다. 파일에서 큰 문자를 읽어 메모리에 올린 뒤 print 한다.
     * 큰 문자는 메모리를 많이 소비하므로 BigChar 인스턴스를 공유하는 방법을 알아 볼 것이다.
     *
     * BigCharFactory는 Bigchar를 만든다. 하지만 같은 문자에 해당하는 BigChar가 이미 만들어 있다면 기존 인스턴스를 사용할 것이다.
     * 지금까지 만든 모든 인스턴스는 pool에 저장된다.
     */

class BigChar {
    private char charName;
    private String fontData;

    public BigChar(char charName) {
        this.charName = charName;
        String filename = "big"+charName+".txt";
        StringBuilder builder = new StringBuilder();
        try {
            for ( String line : Files.readAllLines(Path.of(filename))) {
                builder.append(line);
                builder.append("\n");
            }

            this.fontData = builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void print(){
        System.out.println(fontData);
    }
}
class BigCharFactory {
    private Map< String, BigChar > pool = new HashMap<>();
    private static BigCharFactory singleton = new BigCharFactory();

    private BigCharFactory() {
    }

    public static BigCharFactory getInstance(){
        return singleton;
    }

    public synchronized BigChar getBigChar(char charName) {
        BigChar bc = pool.get(String.valueOf(charName));
        if(Objects.isNull( bc ) ){
            bc = new BigChar(charName);
            pool.put(String.valueOf(charName), bc);
        }

        return bc;
    }
}
class BigString {
    private BigChar[] bigChars;

    public BigString(String text) {
        BigCharFactory factory = BigCharFactory.getInstance();
        bigChars = new BigChar[text.length()];
        for ( int i = 0; i< bigChars.length; i++){
            bigChars[i] = factory.getBigChar(text.charAt(i));
        }
    }
    public void print() {
        for (BigChar bc : bigChars){
            bc.print();
        }
    }
}
public class C20_FlyWeight {
    @Test
    void flyweightTest() {
        BigString bs = new BigString("0710");
        bs.print();
    }
}

/**
 * flyWeight : 평소처럼 다루면 프로그램이 무거워지기 때문에, 공유하는 편이 나은 것을 나타내느다.
 * flyWeightFactory : flyweight를 만드는 공장이다.
 * client: flyweight를 사용해서 flyweight를 만들고 이용한다. (위에서는 BigString)
 *
 *
 *
 *
 *
 *
 *
 *                  intrinsic vs extrinsic
 * 공유하는 정보는 intrinsic한 정보라고 한다. (본래 갖춰진, 본질적인) 즉, 그 인스턴스를 어디로 가져가도 변하지 않는 정보, 상태에 의존하지 않는 정보이다.
 * 반면 공유하지 않는 정보는  extrinsic한 정보라고 한다. (외부에서 온, 비본질적인) 인스턴스 배치 장소에 따라 변하는 정보, 상황에 따라 변하는 정보, 상태에
 * 의존하는 정보이다.
 *
 */