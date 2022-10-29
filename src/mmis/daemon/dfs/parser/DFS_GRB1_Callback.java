package mmis.daemon.dfs.parser;

public interface DFS_GRB1_Callback {

	boolean callback(final DFS_GRB1_INF inf, final float[][] dfsData, int index);

}
