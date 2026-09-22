package dook.util;

import java.util.Map;
import java.util.HashMap;

public class DamerauLevenshtein {
    private record Pair(String textA, String textB) {}

    private static Map<Pair, Integer> cache = new HashMap<>();

    public static int getOsaDistance(String textA, String textB) {
        Pair cacheKey = new Pair(textA, textB);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        int lengthA = textA.length();
        int lengthB = textB.length();

        int[][] damerauLevenshteinMatrix =
            new int[lengthA + 1][lengthB + 1];

        for (int i = 1; i <= lengthA; i++) {
            damerauLevenshteinMatrix[i][0] = i;
        }
        for (int j = 1; j <= lengthB; j++) {
            damerauLevenshteinMatrix[0][j] = j;
        }

        for (int i = 1; i <= lengthA; i++) {
            for (int j = 1; j <= lengthB; j++) {
                int cost = textA.charAt(i - 1) == textB.charAt(j - 1) ? 0 : 1;

                damerauLevenshteinMatrix[i][j] = Math.min(Math.min(
                    damerauLevenshteinMatrix[i - 1][j] + 1,
                    damerauLevenshteinMatrix[i][j - 1] + 1),
                    damerauLevenshteinMatrix[i - 1][j - 1] + cost
                );

                if (i > 1 && j > 1
                        && textA.charAt(i - 1) == textB.charAt(j - 2)
                        && textA.charAt(i - 2) == textB.charAt(j - 1)) {
                    damerauLevenshteinMatrix[i][j] = Math.min(
                        damerauLevenshteinMatrix[i][j],
                        damerauLevenshteinMatrix[i - 2][j - 2] + cost
                    );
                }
            }
        }

        int distance = damerauLevenshteinMatrix[lengthA][lengthB];
        cache.put(cacheKey, distance);
        return distance;
    }
}
