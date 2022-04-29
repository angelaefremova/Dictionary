package javacomp2080assignment2;

public class wordInfo {

    public String word;
    public Meaning meaning;
    public wordInfo left, right;

      public wordInfo(String word, String meaning) {
        this.word = word.toLowerCase();
        this.meaning = new Meaning(meaning);
        this.left = this.right = null;
    }
    public wordInfo(String word, Meaning meaning) {
        this.word = word.toLowerCase();
        this.meaning = meaning;
        this.left = this.right = null;
    }

    public void addMeaning(String meaning) {
        Meaning new_meaning = new Meaning(meaning);
        if (this.meaning.meaning == "" || this.meaning.meaning == "< Not defined >" || this.meaning == null) {
            this.meaning = new_meaning;
        } else if (this.meaning.next == null) {
            this.meaning.next = new_meaning;
        } else if (this.meaning.next != null) {
            Meaning pointer = this.meaning.next;
            while (pointer.next != null) {
                pointer = pointer.next;
            }
            pointer.next = new_meaning;
        }
    }

    public String PrintWithMeaning() {
        String output = this.word;
        Meaning pointer;
        pointer = this.meaning;
        int i = 1;
        while (pointer != null) {
            output += "\n\t" + i + ". " + pointer.meaning;
            i++;
            pointer = pointer.next;
        }
        return output;
    }

    public String PrintWords() {
        String output = this.word;
        Meaning pointer;
        pointer = this.meaning;
        int i = 1;
        while (pointer != null) {
            i++;
            pointer = pointer.next;
        }
        return output;
    }

}
