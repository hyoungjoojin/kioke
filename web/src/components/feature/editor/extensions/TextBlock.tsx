import type { BlockAttributes, BlockOptions } from '.';
import { BlockOperationType, BlockType } from '@/types/page';
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
import { useEffect } from 'react';
import { v4 as uuidv4 } from 'uuid';

type TextBlockAttributes = BlockAttributes;

const TextBlock = Node.create<BlockOptions>({
  name: BlockType.TEXT_BLOCK,
  group: 'block',
  content: 'inline*',
  addExtensions() {
    return [Text, HardBreak, Bold];
  },
  addOptions() {
    return {
      pageId: '',
    };
  },
  addAttributes() {
    return {
      blockId: {
        default: null,
      },
      pageId: {
        default: () => {
          if (!this.options.pageId) {
            throw new Error('Page ID must be configured for blocks');
          }
          return this.options.pageId;
        },
      },
      ops: {
        default: [],
      },
    };
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
        if (editor.getText().length > 0) {
          return false;
        }

        return editor.commands.joinBackward() || true;
      },
      ArrowUp: ({ editor }) => {
        return editor.commands.selectNodeBackward();
      },
    };
  },
  addNodeView() {
    return ReactNodeViewRenderer(TextBlockComponent);
  },
});

function TextBlockComponent({ node, updateAttributes }: NodeViewProps) {
  const { pageId, blockId, ops } = node.attrs as TextBlockAttributes;

  useEffect(() => {
    if (blockId === null) {
      setTimeout(() => {
        const blockId = uuidv4();

        updateAttributes({
          blockId,
          ops: [
            ...ops,
            {
              timestamp: Date.now(),
              op: BlockOperationType.UPDATE,
              pageId,
              blockId,
              type: BlockType.TEXT_BLOCK,
              content: {
                text: '',
              },
            },
          ],
        } satisfies Partial<TextBlockAttributes>);
      }, 0);
    }
  }, [blockId, pageId, ops, updateAttributes]);

  return (
    <NodeViewWrapper>
      <div>
        <NodeViewContent />
      </div>
    </NodeViewWrapper>
  );
}

export type { TextBlockAttributes };
export { TextBlock };
