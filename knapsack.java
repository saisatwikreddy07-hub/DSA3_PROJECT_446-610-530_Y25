public class Knapsack {

    static int solve(int[] weight, int[] value, int capacity) {

        int n = weight.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {

            for (int w = 1; w <= capacity; w++) {

                if (weight[i - 1] <= w) {
                    dp[i][w] = Math.max(
                        value[i - 1] + dp[i - 1][w - weight[i - 1]],
                        dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        return dp[n][capacity];
    }

    public static void main(String[] args) {

        int[] weight = {2, 5, 7, 3};
        int[] value = {20, 30, 45, 25};

        int capacity = 10;

        System.out.println(
            "Maximum Value: " +
            solve(weight, value, capacity)
        );
    }
}