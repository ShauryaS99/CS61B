public class Palindrome {

    /**Returns Deque with the letters of the word passed*/
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<Character>();
        if (word.length() <= 0) {
            return deque;
        } else {
            int index = 0;
            while (index < word.length()) {
                char letter = word.charAt(index);
                deque.addLast(letter);
                index++;
            }
            return deque;
        }

    }

    /**Checks if the word is a palindrome*/
    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return helperisPalindrome(deque);
    }

    /**Helper method to check if the word is a palindrome */
    private boolean helperisPalindrome(Deque<Character> deque) {
        if (deque.isEmpty() || deque.size() <= 1) {
            return true;
        }

        char first = deque.removeFirst();
        char last =  deque.removeLast();

        if (first != last) {
            return false;
        }
        return helperisPalindrome(deque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return helperisPalindrome(deque, cc);
    }

    private boolean helperisPalindrome(Deque<Character> deque, CharacterComparator cc) {
        if (deque.isEmpty() || deque.size() <= 1) {
            return true;
        }

        char first = deque.removeFirst();
        char last =  deque.removeLast();

        if (!cc.equalChars(first, last)) {
            return false;
        }
        return helperisPalindrome(deque, cc);
    }

}
