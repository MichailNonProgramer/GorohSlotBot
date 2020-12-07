package games;

import bot.User;
import org.glassfish.grizzly.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class SlotsPatterns {
    private final Slot[][] table;
    private int benefit;
    private final ArrayList<String> winSlotsArr;

    public ArrayList<String> getWinSlotsArr() {
        return winSlotsArr;
    }

    public int getBenefit() {
        return benefit;
    }

    public SlotsPatterns(Slot[][] table) {
        this.table = table;
        this.winSlotsArr = new ArrayList<>();
        this.benefit = 0;
    }

//    public void logWinPattern() {
//        var copy = table;
//        var slot = new Slot(Emoji.background.getEmojiCode(), 0);
//        copy = new Slot[][]{
//                {slot, slot, slot, slot, slot},
//                {slot, slot, slot, slot, slot},
//                {slot, slot, slot, slot, slot},
//                {slot, slot, slot, slot, slot}};
//        copy[2][0] = table[2][0];
//        copy[1][1] = table[1][1];
//        copy[0][2] = table[0][2];
//        copy[1][3] = table[1][3];
//        copy[2][4] = table[2][4];
//        var result = new StringBuilder();
//        for (Slot[] slots : copy) {
//            result.append(slots[0].getCode());
//            result.append(slots[1].getCode());
//            result.append(slots[2].getCode());
//            result.append(slots[3].getCode());
//            result.append(slots[4].getCode());
//            result.append("\n");
//        }
//        System.out.println(result.toString());
//    }

    public void CheckCollisionsAndAddMoney(User user){
        var patternsArr = new ArrayList<>(
                Arrays.asList(
                        patternLine1(),
                        patternLine2(),
                        patternLine3(),
                        patternLine4(),
                        patternV(),
                        patternReverseV(),
                        patternDiagonal1(),
                        patternDiagonalReverse1(),
                        patternDiagonal2(),
                        patternDiagonalReverse2(),
//                        patternDiagonal3(),
//                        patternDiagonalReverse3(),
//                        patternEarth(),
//                        patternEarthReverse(),
//                        patternEarth2(),
//                        patternEarthReverse2(),
                        patternW(),
                        patternM()
//                        patternZig1(),
//                        patternZigReverse1(),
//                        patternZig2(),
//                        patternZigReverse2(),
//                        patternNipple1(),
//                        patternNippleReverse1(),
//                        patternNipple2(),
//                        patternNippleReverse2(),
//                        patternOddDiagonal1(),
//                        patternOddDiagonalReverse1(),
//                        patternOddDiagonal2(),
//                        patternOddDiagonalReverse2()
                ));
        for (var pair : patternsArr)
        {
            if(pair.getFirst() != 0) {
                winSlotsArr.add(pair.getSecond().getCode());
                benefit += receiveMoney(pair, user);
            }
        }
    }

    private int receiveMoney(Pair<Integer, Slot> pair, User user){
        var currentBenefit = (int) (user.getBet() * pair.getFirst() * pair.getSecond().getMultiplier());
        user.AddMoney(currentBenefit);
        return currentBenefit;
    }

    private Pair<Integer, Slot> parsePattern(
            Slot num1,
            Slot num2,
            Slot num3,
            Slot num4,
            Slot num5) {
        var winSlot = num1.getCode();
        var slots = new ArrayList<String>();
        var count = 0;
        var slot = "";

        slots.add(num1.getCode());
        slots.add(num2.getCode());
        slots.add(num3.getCode());
        slots.add(num4.getCode());
        slots.add(num5.getCode());

        for (String s : slots) {
            slot = s;
            if (!slot.equals(winSlot)) {
                break;
            }
            count += 1;
        }
        // в конечном итоге множитель слота домножается на первое число в паре
        return switch (count) {
            case 3 -> new Pair<>(1, num1);
            case 4 -> new Pair<>(2, num1);
            case 5 -> new Pair<>(10, num1);
            default -> new Pair<>(0, num1);
        };
    }
    // ------
    private Pair<Integer, Slot> patternLine1(){
        return parsePattern(table[0][0], table[0][1], table[0][2], table[0][3], table[0][4]);
    }

    private Pair<Integer, Slot> patternLine2(){
        return parsePattern(table[1][0], table[1][1], table[1][2], table[1][3], table[1][4]);
    }

    private Pair<Integer, Slot> patternLine3(){
        return parsePattern(table[2][0], table[2][1], table[2][2], table[2][3], table[2][4]);
    }

    private Pair<Integer, Slot> patternLine4(){
        return parsePattern(table[3][0], table[3][1], table[3][2], table[3][3], table[3][4]);
    }
    // v
    private Pair<Integer, Slot> patternV(){
        return parsePattern(table[1][0], table[2][1], table[3][2], table[2][3], table[1][4]);
    }
    // ^
    private Pair<Integer, Slot> patternReverseV(){
        return parsePattern(table[2][0], table[1][1], table[0][2], table[1][3], table[2][4]);
    }
    // \_
    private Pair<Integer, Slot> patternDiagonal1(){
        return parsePattern(table[0][0], table[1][1], table[2][2], table[3][3], table[3][4]);
    }
    // /-
    private Pair<Integer, Slot> patternDiagonalReverse1(){
        return parsePattern(table[3][0], table[2][1], table[1][2], table[0][3], table[0][4]);
    }
    // -\
    private Pair<Integer, Slot> patternDiagonal2(){
        return parsePattern(table[0][0], table[0][1], table[1][2], table[2][3], table[3][4]);
    }
    // _/
    private Pair<Integer, Slot> patternDiagonalReverse2(){
        return parsePattern(table[3][0], table[3][1], table[2][2], table[1][3], table[0][4]);
    }
//    // \-\
//    private Pair<Integer, Slot> patternDiagonal3(){
//        return parsePattern(table[0][0], table[1][1], table[2][2], table[2][3], table[3][4]);
//    }
//    // /-/
//    private Pair<Integer, Slot> patternDiagonalReverse3(){
//        return parsePattern(table[3][0], table[2][1], table[1][2], table[1][3], table[0][4]);
//    }
//    // -___-
//    private Pair<Integer, Slot> patternEarth(){
//        return parsePattern(table[2][0], table[3][1], table[3][2], table[3][3], table[2][4]);
//    }
//    // _---_
//    private Pair<Integer, Slot> patternEarthReverse(){
//        return parsePattern(table[1][0], table[0][1], table[0][2], table[0][3], table[1][4]);
//    }
//    // -___-
//    private Pair<Integer, Slot> patternEarth2(){
//        return parsePattern(table[0][0], table[1][1], table[1][2], table[1][3], table[0][4]);
//    }
//    // _---_
//    private Pair<Integer, Slot> patternEarthReverse2(){
//        return parsePattern(table[3][0], table[2][1], table[2][2], table[2][3], table[3][4]);
//    }
    // W
    private Pair<Integer, Slot> patternW(){
        return parsePattern(table[0][0], table[1][1], table[0][2], table[1][3], table[0][4]);
    }
    // M
    private Pair<Integer, Slot> patternM(){
        return parsePattern(table[3][0], table[2][1], table[3][2], table[2][3], table[3][4]);
    }
//    // \/\
//    private Pair<Integer, Slot> patternZig1(){
//        return parsePattern(table[2][0], table[3][1], table[2][2], table[1][3], table[2][4]);
//    }
//    // /\/
//    private Pair<Integer, Slot> patternZigReverse1(){
//        return parsePattern(table[1][0], table[0][1], table[1][2], table[2][3], table[1][4]);
//    }
//    // \/\
//    private Pair<Integer, Slot> patternZig2(){
//        return parsePattern(table[1][0], table[2][1], table[1][2], table[0][3], table[1][4]);
//    }
//    // /\/
//    private Pair<Integer, Slot> patternZigReverse2(){
//        return parsePattern(table[2][0], table[1][1], table[2][2], table[3][3], table[2][4]);
//    }
//    // __-__
//    private Pair<Integer, Slot> patternNipple1(){
//        return parsePattern(table[3][0], table[3][1], table[2][2], table[3][3], table[3][4]);
//    }
//    // --_--
//    private Pair<Integer, Slot> patternNippleReverse1(){
//        return parsePattern(table[2][0], table[2][1], table[3][2], table[2][3], table[2][4]);
//    }
//    // __-__
//    private Pair<Integer, Slot> patternNipple2(){
//        return parsePattern(table[1][0], table[1][1], table[0][2], table[1][3], table[1][4]);
//    }
//    // --_--
//    private Pair<Integer, Slot> patternNippleReverse2(){
//        return parsePattern(table[0][0], table[0][1], table[1][2], table[0][3], table[0][4]);
//    }
//    // -\_
//    private Pair<Integer, Slot> patternOddDiagonal1(){
//        return parsePattern(table[1][0], table[1][1], table[2][2], table[3][3], table[3][4]);
//    }
//    // _/-
//    private Pair<Integer, Slot> patternOddDiagonalReverse1(){
//        return parsePattern(table[2][0], table[2][1], table[1][2], table[0][3], table[0][4]);
//    }
//    // +--_
//    private Pair<Integer, Slot> patternOddDiagonal2(){
//        return parsePattern(table[1][0], table[2][1], table[2][2], table[2][3], table[3][4]);
//    }
//    // -++*
//    private Pair<Integer, Slot> patternOddDiagonalReverse2(){
//        return parsePattern(table[2][0], table[1][1], table[1][2], table[1][3], table[0][4]);
//    }
}
