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
import { v4 as uuidv4 } from 'uuid';

interface TextBlockOptions {}

const TextBlock = Node.create({
  name: BlockType.TEXT_BLOCK,
  group: 'block',
  content: 'inline*',
  addOptions() {
    return {} as TextBlockOptions;
  },
  addAttributes() {
    return {
      blockId: {
        default: () => {
          return uuidv4();
        },
      },
      isNew: {
        default: true,
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
      Backspace: ({ editor }) => {
        return editor.commands.joinBackward();
      },
    };
  },
  addNodeView() {
    return ReactNodeViewRenderer(TextBlockComponent);
  },
});

function TextBlockComponent({}: NodeViewProps) {
  return (
    <NodeViewWrapper>
      <div>
        <NodeViewContent />
      </div>
    </NodeViewWrapper>
  );
}

export { TextBlock };
