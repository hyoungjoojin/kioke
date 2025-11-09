import { NodeViewContent, type NodeViewProps } from '@tiptap/react';

function TextBlockComponent({}: NodeViewProps) {
  return (
    <div>
      <NodeViewContent />
    </div>
  );
}

export default TextBlockComponent;
