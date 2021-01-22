public class Main
{
    public static void main(String[] args)
    {
        HashMap<Integer, String> map = new HashMap<>();
        map.Place(1 , "A");
        map.Place(2 , "B");
        map.Place(3 , "C");
        map.Place(4 , "D");
        map.Place(5 , "E");
        map.Place(6 , "F");
        map.Place(7 , "G");
        map.Place(8 , "H");
        map.Place(9 , "I");
        map.Place(10, "J");
        map.Place(11, "K");
        map.Place(12, "L");
        map.Place(13, "M");
        map.Place(14, "N");
        map.Place(15, "O");
        map.Place(16, "P");
        map.Place(17, "Q");
        map.Place(18, "R");
        map.Place(19, "S");

        System.out.println(map.Get(1 ));
        System.out.println(map.Get(2 ));
        System.out.println(map.Get(3 ));
        System.out.println(map.Get(4 ));
        System.out.println(map.Get(5 ));
        System.out.println(map.Get(6 ));
        System.out.println(map.Get(7 ));
        System.out.println(map.Get(8 ));
        System.out.println(map.Get(9 ));
        System.out.println(map.Get(10));
        System.out.println(map.Get(11));
        System.out.println(map.Get(12));
        System.out.println(map.Get(13));
        System.out.println(map.Get(14));
        System.out.println(map.Get(15));
        System.out.println(map.Get(16));
        System.out.println(map.Get(17));
        System.out.println(map.Get(18));
        System.out.println(map.Get(19));

        map.Remove(1);
        map.Remove(2);
        map.Remove(3);
        map.Remove(4);
        map.Remove(5);
        map.Remove(6);

        System.out.println(map.Get(1 ));
        System.out.println(map.Get(2 ));
        System.out.println(map.Get(3 ));
        System.out.println(map.Get(4 ));
        System.out.println(map.Get(5 ));
        System.out.println(map.Get(6 ));
        System.out.println(map.Get(7 ));
        System.out.println(map.Get(8 ));
        System.out.println(map.Get(9 ));
        System.out.println(map.Get(10));
        System.out.println(map.Get(11));
        System.out.println(map.Get(12));
        System.out.println(map.Get(13));
        System.out.println(map.Get(14));
        System.out.println(map.Get(15));
        System.out.println(map.Get(16));
        System.out.println(map.Get(17));
        System.out.println(map.Get(18));
        System.out.println(map.Get(19));

        map.Clear();

        System.out.println(map.Get(1 ));
        System.out.println(map.Get(2 ));
        System.out.println(map.Get(3 ));
        System.out.println(map.Get(4 ));
        System.out.println(map.Get(5 ));
        System.out.println(map.Get(6 ));
        System.out.println(map.Get(7 ));
        System.out.println(map.Get(8 ));
        System.out.println(map.Get(9 ));
        System.out.println(map.Get(10));
        System.out.println(map.Get(11));
        System.out.println(map.Get(12));
        System.out.println(map.Get(13));
        System.out.println(map.Get(14));
        System.out.println(map.Get(15));
        System.out.println(map.Get(16));
        System.out.println(map.Get(17));
        System.out.println(map.Get(18));
        System.out.println(map.Get(19));
    }
}
