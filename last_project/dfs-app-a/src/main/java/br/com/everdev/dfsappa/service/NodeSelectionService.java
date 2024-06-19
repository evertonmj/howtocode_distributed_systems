package br.com.everdev.dfsappa.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class NodeSelectionService {
    private final List<String> nodes = Arrays.asList("dfs-app-b", "dfs-app-c");

    public String selectRandomNode() {
        Random random = new Random();
        return nodes.get(random.nextInt(nodes.size()));
    }
}