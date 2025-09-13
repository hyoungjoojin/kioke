import type { BlockAttributes } from '.';
import { BlockType } from '@/constant/block';
import Bold from '@tiptap/extension-bold';
import HardBreak from '@tiptap/extension-hard-break';
import Text from '@tiptap/extension-text';
import {
  Node,
  NodeViewContent,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';

interface TextBlockOptions {}

type TextBlockAttributes = BlockAttributes & {};

export const TextBlock = Node.create({
  name: BlockType.TEXT_BLOCK,
  group: 'block',
  content: 'inline*',
  addOptions() {
    return {} as TextBlockOptions;
  },
  addAttributes() {
    return {
      blockId: {
        default: null,
      },
    };
  },
  addExtensions() {
    return [Text, HardBreak, Bold];
  },
  renderHTML({ HTMLAttributes }) {
    return [BlockType.TEXT_BLOCK, mergeAttributes(HTMLAttributes)];
  },
  parseHTML() {
    return [
      {
        tag: BlockType.TEXT_BLOCK,
      },
    ];
  },
  addKeyboardShortcuts() {
    return {
      Enter: ({ editor }) => editor.commands.setHardBreak(),
      Tab: ({ editor }) => {
        editor.commands.insertContent('  ');
        return true;
      },
    };
  },
  addNodeView() {
    return ReactNodeViewRenderer(TextBlockComponent);
  },
});

function TextBlockComponent({ node }: NodeViewProps) {
  const {} = node.attrs as TextBlockAttributes;

  return (
    <NodeViewWrapper>
      <div>
        <NodeViewContent />
      </div>
    </NodeViewWrapper>
  );
}
