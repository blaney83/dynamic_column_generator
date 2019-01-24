package io.github.blaney83;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "DynamicColumnGenerator" Node.
 * A dynamic column generator for appending columns to tables to assist with workflow testing, visualization techniques and general validation.
 *
 * @author Ben Laney
 */
public class DynamicColumnGeneratorNodeFactory 
        extends NodeFactory<DynamicColumnGeneratorNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DynamicColumnGeneratorNodeModel createNodeModel() {
        return new DynamicColumnGeneratorNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<DynamicColumnGeneratorNodeModel> createNodeView(final int viewIndex,
            final DynamicColumnGeneratorNodeModel nodeModel) {
        return new DynamicColumnGeneratorNodeView(nodeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new DynamicColumnGeneratorNodeDialog();
    }

}

