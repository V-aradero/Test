import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Solution {
    public static class Layer {
        public int coefficient;
        public StringBuilder line;

        public Layer(int coefficient) {
            this.coefficient = coefficient;
            line = new StringBuilder();
        }
    }
    public static Stack<Layer> stack = new Stack<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));      // читает поток
        String line = reader.readLine();
        System.out.println("Строка валидна? " + validate(line));
        char[] result = line.toCharArray();                                                // приводит строку к массиву символов
        int ch = -1;
        int coefficient = 0;
        for(int i = 0; i<result.length; i++) {                                              // читает массив символов
            try {
                ch =  result[i];
            } catch (Exception e) {
                System.exit(1);
            }
            if (ch == -1) {
                break;
            }
            if (Character.isDigit(ch)) {                               // если символ - это цифра
                coefficient = 10 * coefficient + Character.getNumericValue(ch);  // возвращает цифру, если это цифра и записывает в coefficient
            } else if (ch == '[') {                                    //иначе если это '['
                stack.push(new Layer(coefficient));                         // то заносит в стек обьект с полем coefficient
                coefficient = 0;                                           // обнуляет coefficient
            } else if (ch == ']') {                                    //иначе если это ']'
                Layer layer = stack.pop();                              // достает обьект с полем coefficient из стека и записывает в layer
                String str = layer.line.toString();                        // Создает строку str и записывает в нее строку из layer
                append(layer.coefficient, str);                               // вызывает перегруженный метод append
                coefficient = 0;                                           // обнуляет coefficient
            } else {                                                  // если не скобки и не цифры вызывает перегруженный метод append
                append(coefficient, Character.toString((char) ch));
                coefficient = 0;                                           // обнуляет coefficient
            }
        }
    }

    public static void append(int coefficient, String str) {                 // метод склеивает/множит строки
        int multiplier;
        if(coefficient==0)
        multiplier = 1;
        else multiplier = coefficient;
        for (int i = 0; i < multiplier; ++i) {                               // запускаем цикл от 0 до multiplayer
            if (!stack.empty()) {
                stack.peek().line.append(str);
            } else {
                System.out.print(str);                                    // вывод результата, если стек пуст
            }
        }
    }

    public static boolean validate(String s) {                           // метод проверки валидности строки

        int count = 0;
        for(int i=0; i<s.length(); i++){

            if (Character.isDigit(s.charAt(i))) {
                count++;
            }
            else if((s.charAt(i)=='[')||(s.charAt(i)==']')) {
                count++;
                if(s.charAt(i)=='[') {
                }
            }
            else if (Character.isAlphabetic(s.charAt(i))) {
                if (Character.UnicodeBlock.of(s.charAt(i)).equals(Character.UnicodeBlock.BASIC_LATIN)){
                    count++;
                }
            }
        }
        if(s.length()!=count) {
            return false;                  // если количество латинских букв + квадр.скобок + цифр не соответствует длинне строки.
        }
        else {
           int n= s.length()-1;                            // проверяем на правильный порядок открывающихся и закрывающихся скобок
            Stack<Character> stack = new Stack<>();
            for(int i=n; i>=0; i--)
            {
                if(s.charAt(i)== ']'){
                    stack.push(s.charAt(i));
                }
                else if(!stack.isEmpty() && s.charAt(i)=='[' && stack.peek()==']'){
                    stack.pop();
                                   }
                else if((s.charAt(i)== '[')) {
                    stack.push(s.charAt(i));
                }
                        }
            if(!stack.isEmpty()){
                return false;
            }
            Stack<Character> stack2 = new Stack<>();             // проверяем на порядок цифр скобок и латиницы в строке
            stack2.push(s.charAt(s.length()-1));
            for(int i=n-1; i>=0; i--) {
                if (Character.isDigit(s.charAt(s.length()-1))||s.charAt(0)=='[') {
                    return false;
                } else if (!stack2.isEmpty() && Character.isDigit(s.charAt(i)) && Character.isAlphabetic(stack2.peek())) {
                   return false;
                }
                else if (!stack2.isEmpty() && Character.isDigit(s.charAt(i)) && stack2.peek() == ']') {
                    return false;
                }
                else if (!stack2.isEmpty() &&  Character.isAlphabetic(s.charAt(i)) && Character.isDigit(stack2.peek())) {
                    return false;
                }
                else if (!stack2.isEmpty() &&  Character.isAlphabetic(s.charAt(i)) && stack2.peek() == '[') {
                    return false;
                }
                else if (!stack2.isEmpty() && s.charAt(i)=='[' && stack2.peek() == '[') {
                    return false;
                }
                else if (!stack2.isEmpty() && s.charAt(i)==']' && stack2.peek() == '[') {
                    return false;
                }
                else if (!stack2.isEmpty() && s.charAt(i)==']' && stack2.peek() == ']') {
                    return false;
                }
                else if (!stack2.isEmpty() && s.charAt(i)=='[' && stack2.peek() == ']') {
                    return false;
                }

                else {
                    stack2.pop();
                    stack2.push(s.charAt(i));
                }
            }
            return true;
        }
    }
}