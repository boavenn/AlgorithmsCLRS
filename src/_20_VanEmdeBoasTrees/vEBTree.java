package _20_VanEmdeBoasTrees;

public class vEBTree<T>
{
    private final int NIL = -1;
    private int universe;
    private vEBTree<T> summary;
    private vEBTree<T>[] cluster;
    private int min;
    private int max;
    private T minValue;
    private T maxValue;

    @SuppressWarnings("unchecked")
    public vEBTree(int universe) {
        this.universe = universe;
        min = NIL;
        max = NIL;

        if (universe == 2) {
            summary = null;
            cluster = null;
        }
        else {
            int upperSquare = upperSquare(universe);
            int lowerSquare = lowerSquare(universe);
            summary = new vEBTree<>(upperSquare);
            cluster = new vEBTree[upperSquare];
            for (int i = 0; i < upperSquare; i++)
                cluster[i] = new vEBTree<>(lowerSquare);
        }
    }

    public int minimum() {
        return min;
    }

    public int maximum() {
        return max;
    }

    public T getValue(int key) {
        if (key == min)
            return minValue;
        else if (key == max)
            return maxValue;
        else if (universe == 2)
            return null;
        else
            return cluster[high(key)].getValue(low(key));
    }

    public boolean contains(int key) {
        if (key == min || key == max)
            return true;
        else if (universe == 2)
            return false;
        else
            return cluster[high(key)].contains(low(key));
    }

    public int successor(int key) {
        if (universe == 2) {
            if (key == 0 && max == 1)
                return 1;
            else
                return NIL;
        }
        else if (min != NIL && key < min)
            return min;
        else {
            int maxLow = cluster[high(key)].maximum();
            if (maxLow != NIL && low(key) < maxLow) {
                int offset = cluster[high(key)].successor(low(key));
                return index(high(key), offset);
            }
            else {
                int succCluster = summary.successor(high(key));
                if (succCluster == NIL)
                    return NIL;
                else {
                    int offset = cluster[succCluster].minimum();
                    return index(succCluster, offset);
                }
            }
        }
    }

    public int predecessor(int key) {
        if (universe == 2) {
            if (key == 1 && min == 0)
                return 0;
            else
                return NIL;
        }
        else if (max != NIL && key > max)
            return max;
        else {
            int minLow = cluster[high(key)].minimum();
            if (minLow != NIL && low(key) > minLow) {
                int offset = cluster[high(key)].predecessor(low(key));
                return index(high(key), offset);
            }
            else {
                int predCluster = summary.predecessor(high(key));
                if (predCluster == NIL) {
                    if (min != NIL && key > min)
                        return min;
                    else
                        return NIL;
                }
                else {
                    int offset = cluster[predCluster].maximum();
                    return index(predCluster, offset);
                }
            }
        }
    }

    private void emptyTreeInsert(int key, T value) {
        min = key;
        max = key;
        minValue = value;
        maxValue = value;
    }

    public void insert(int key, T value) {
        if (min == NIL)
            emptyTreeInsert(key, value);
        else {
            if (key < min) {
                int temp1 = min;
                min = key;
                key = temp1;
                T temp2 = minValue;
                minValue = value;
                value = temp2;
            }
            if (universe > 2) {
                if (cluster[high(key)].minimum() == NIL) {
                    summary.insert(high(key), value);
                    cluster[high(key)].emptyTreeInsert(low(key), value);
                }
                else
                    cluster[high(key)].insert(low(key), value);
            }
            if (key > max) {
                max = key;
                maxValue = value;
            }
        }
    }

    public void remove(int key) {
        if (min == max) {
            min = NIL;
            max = NIL;
            minValue = null;
            maxValue = null;
        }
        else if (universe == 2) {
            if (key == 0) {
                min = 1;
                minValue = maxValue;
            }
            else
                min = 0;
            max = min;
            maxValue = minValue;
        }
        else {
            if (key == min) {
                int firstCluster = summary.minimum();
                key = index(firstCluster, cluster[firstCluster].minimum());
                minValue = getValue(key);
                min = key;
            }
            cluster[high(key)].remove(low(key));
            if (cluster[high(key)].minimum() == NIL) {
                summary.remove(high(key));
                if (key == max) {
                    int summaryMax = summary.maximum();
                    if (summaryMax == NIL) {
                        max = min;
                        maxValue = minValue;
                    }
                    else {
                        int x = index(summaryMax, cluster[summaryMax].maximum());
                        maxValue = getValue(x);
                        max = x;
                    }
                }
            }
            else if (key == max) {
                int x = index(high(key), cluster[high(key)].maximum());
                maxValue = getValue(x);
                max = x;
            }
        }
    }

    private int high(int x) {
        return x / lowerSquare(universe);
    }

    private int low(int x) {
        return x % lowerSquare(universe);
    }

    private int index(int x, int y) {
        return x * lowerSquare(universe) + y;
    }

    private int lowerSquare(int u) {
        return (int) Math.pow(2d, Math.floor((Math.log(u) / Math.log(2d) / 2d)));
    }

    private int upperSquare(int u) {
        return (int) Math.pow(2d, Math.ceil((Math.log(u) / Math.log(2d) / 2d)));
    }

    private static class Example
    {
        public static void main(String[] args) {
            vEBTree<String> veb = new vEBTree<>(16);
            veb.insert(7, "7th");
            veb.insert(3, "3rd");
            veb.insert(1, "1st");
            veb.insert(8, "8th");
            veb.insert(5, "5th");
            veb.insert(2, "2nd");
            veb.insert(6, "6th");
            veb.insert(4, "4th");

            int min = veb.minimum();
            do {
                System.out.print(veb.getValue(min) + " ");
                veb.remove(min);
                min = veb.minimum();
            } while (min != -1);
        }
    }
}
