//For Gacha.java by Chris Naing
public class Node {
    //name of unit
    private String name;
    //rarity of unit (3, 4, or 5 star)
    private int rarity;
    //amount of unit currently pulled
    private int amount;
    //amount of points unit is worth for point system
    private int points;
    //left and right branches for binary tree
    private Node left;
    private Node right;
    //constructors
    public Node(){}
    //constructor for binary tree
    public Node(String nm, int rare){
        name = nm;
        rarity = rare;
        amount = 1;
        left = null;
        right = null;
    }
    //constructor for stack
    public Node(String nm, int rare, int pt){
        name = nm;
        rarity = rare;
        points = pt;
    }
    //getters
    public String getName(){
        return name;
    }
    public int getRarity(){
        return rarity;
    }
    public Node getLeft(){
        return left;
    }
    public Node getRight(){
        return right;
    }
    public int getPoints(){
        return points;
    }
    //setters
    public void setRight(Node newNode){
        right = newNode;
    }
    public void setLeft(Node newNode){
        left = newNode;
    }
    public void setName(String nm){
        name = nm;
    }
    public void setRarity(int rare){
        rarity = rare;
    }
    //increments amount
    public void addAmount(){
        amount++;
    }
    //prints data of each pull for printPulls()
    public void printData(Node unit){
        System.out.println(name);
        System.out.println(rarity + " star");
        System.out.println("Amount pulled this session: " + amount);
    }
}
