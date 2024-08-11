import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        for (int s = 0; s < 100; s++) {
            sizeToFreq.put(s, 0);
        }
        Runnable logic = () -> {
            String route = generateRoute("RLRFR", 100);
            int countR = 0;
            for (int j = 0; j < route.length(); j++) {
                if (String.valueOf(route.charAt(j)).equals("R")) {
                    countR++;
                }
            }
            synchronized (sizeToFreq) { //Синхронизируем мапу
                sizeToFreq.put(countR, sizeToFreq.get(countR) + 1);
            }
            System.out.println("Count of R: " + countR);
        };
        for (int i = 0; i < 100; i++) { // Запускаем потоки
            Thread thread = new Thread(logic);
            thread.start();
            thread.join();
        }
        int max = -1; // Находим максимальное число повторений
        int maxKey = -1;
        for (int k = 0; k < sizeToFreq.size(); k++) {
            if (sizeToFreq.get(k) > max) {
                max = sizeToFreq.get(k);
                maxKey = k;
            }
        }
        System.out.println("Самое частое количество повторений " + maxKey + " (" + max + ") раз\n" +
                "Другие размеры:");
        for (int k = 0; k < sizeToFreq.size(); k++) {
            System.out.println("- " + k + " (" + sizeToFreq.get(k) + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}