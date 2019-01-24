package io.github.blaney83;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "DynamicColumnGenerator" Node.
 * A dynamic column generator for appending columns to tables to assist with workflow testing, visualization techniques and general validation.
 *
 * @author Ben Laney
 */
public class DynamicColumnGeneratorNodeView extends NodeView<DynamicColumnGeneratorNodeModel> {

    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link DynamicColumnGeneratorNodeModel})
     */
    protected DynamicColumnGeneratorNodeView(final DynamicColumnGeneratorNodeModel nodeModel) {
        super(nodeModel);

        // TODO instantiate the components of the view here.

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {

        // TODO retrieve the new model from your nodemodel and 
        // update the view.
        DynamicColumnGeneratorNodeModel nodeModel = 
            (DynamicColumnGeneratorNodeModel)getNodeModel();
        assert nodeModel != null;
        
        // be aware of a possibly not executed nodeModel! The data you retrieve
        // from your nodemodel could be null, emtpy, or invalid in any kind.
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onClose() {
    
        // TODO things to do when closing the view
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onOpen() {

        // TODO things to do when opening the view
    }

}

