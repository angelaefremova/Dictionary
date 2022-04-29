package javacomp2080assignment2;

public class dictionary {

    private wordInfo data;
    private int numOfWords;
    private MyQueue<wordInfo> myQueue;

    public dictionary() {
        this.data = null;
        this.numOfWords = 0;
    }

    // This function is used to indicate the position
    // of a word in the binary tree
    public int word_indicator(String word, int num) {
        int total = 0;
        for (int i = 0; i < num; i++) {
            total += word.toLowerCase().charAt(i);
        }
        return total;
    }

   public boolean words_compare(String word_1, String word_2) {
        int length = word_1.length() < word_2.length() ? word_1.length() : word_2.length();
        int i = 1;
        int word_1_val = this.word_indicator(word_1, i);
        int word_2_val = this.word_indicator(word_2, i);
        while ((i < length) && (word_1_val == word_2_val)) {
            i++;
            word_1_val = this.word_indicator(word_1, i);
            word_2_val = this.word_indicator(word_2, i);
        }
	   if (word_1_val == word_2_val) {
         if (word_1.length() > word_2.length() && (!(word_1.length() < (i + 1)))) {
            word_1_val = word_indicator(word_1, i + 1);
          }
      else if (!(word_2.length() < (i + 1))){
        word_2_val = word_indicator(word_2, i + 1);
      }
    }
   if (word_1_val < word_2_val) {
            return true;
        }
        return false;
    }

   
    public boolean add(String word, String meaning) {
        if (exists(word)) {
            return false;
        }

        add_word(word, meaning);
        return true;

    }



    private void add_word(String word, String meaning) {
        wordInfo new_word = new wordInfo(word, meaning);
        if (this.data == null) {
            this.data = new_word;
        } else {
            wordInfo prev, curr;
            prev = curr = this.data;
            while (curr != null) {
                prev = curr;
                if (this.words_compare(word, curr.word)) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }
            }
            if (this.words_compare(word, prev.word)) {
                prev.left = new_word;
            } else {
                prev.right = new_word;
            }
        }  
    }

    public void addMeaning(String word, String meaning) {
        wordInfo found_word = Search(word);
        if (found_word != null) {
            wordInfo curr = this.data;
            if (this.data != null) {
                while (curr != null && (!(curr.word.toLowerCase().equals(word.toLowerCase())))) {
                    if (words_compare(word, curr.word)) {
                        curr = curr.left;
                    } else {
                        curr = curr.right;
                    }
                }
                if (curr != null) {
                    curr.addMeaning(meaning);
                };
            }
            System.out.println("Added new meaning to " + word + " successfully.");
            return;
        }
        System.out.println("Failed to add new meaning to " + word);
        System.out.println(word + " not found.");
    }

    public void printDictionary() {
        printDictonaryHandler(this.data);
    }

    public void printDictonaryHandler(wordInfo word) {
        if (word != null) {
            printDictonaryHandler(word.left);
            System.out.println(word.PrintWithMeaning());
            printDictonaryHandler(word.right);
        }
    }

    public void printWordList() {
        printWordHandler(this.data);
    }

    public void printWordHandler(wordInfo word) {
        if (word != null) {
            printWordHandler(word.left);
            System.out.println(word.PrintWords());
            printWordHandler(word.right);
        }
    }

    public wordInfo Search(String word) {
        wordInfo curr = this.data;
        if (this.data == null) {
            return null;
        }
        while (curr != null && (!(curr.word.toLowerCase().equals(word.toLowerCase())))) {
            if (words_compare(word, curr.word)) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        if (curr != null) {
            return curr;
        }
        return null;
    }
    
    

    public boolean exists(String word) {
        return Search(word) != null;
    }

    public void deleteMethod(String word) {
        this.data = WordMapping(this.data, word);
    }
    
    public boolean delete(String word){
        deleteMethod(word);
        return true;
    }

    public wordInfo WordMapping(wordInfo root, String word) {
        if (root == null) {
            return root;
        }

        if (words_compare(word, root.word)) {
            root.left = WordMapping(root.left, word);
        } else if (words_compare(root.word, word)) {
            root.right = WordMapping(root.right, word);
        } else {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }

            wordInfo branch = root.right;
            while (branch.left != null) {
                branch = branch.left;
            }
            root.word = branch.word;
            root.right = WordMapping(root.right, branch.word);
        }
        return root;
    }

    private void Remapping() {
        wordInfo[] inOrderArray = this.inOrderExport();
        int lo = 0, hi = (inOrderArray.length - 1);
        this.data = null;
        this.data = Divider(inOrderArray, lo, hi);
    }

    private wordInfo Divider(wordInfo[] word_list, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        int mid = (lo + hi) / 2;
        wordInfo word = new wordInfo(word_list[mid].word, word_list[mid].meaning);
        word.left = Divider(word_list, lo, mid - 1);
        word.right = Divider(word_list, mid + 1, hi);
        return word;
    }

    private wordInfo[] inOrderExport() {
        this.myQueue = new MyQueue<>();
        inOrderVisit(this.data);
        int i = 0;
        wordInfo[] temp = new wordInfo[this.myQueue.length()];
        while (!this.myQueue.isEmpty()) {
            wordInfo pop = this.myQueue.deQueue();
            temp[i] = pop;
            i++;
        }
        return temp;
    }

    private void inOrderVisit(wordInfo curr) {
        if (curr != null) {
            inOrderVisit(curr.left);
            this.myQueue.enQueue(curr);
            inOrderVisit(curr.right);
        }
    }

}
