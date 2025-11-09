import type { BlockAttributes, BlockOptions } from '.';
import BlockWrapper from '../blocks/BlockWrapper';
import TextBlockComponent from '../blocks/TextBlockComponent';
import { BlockOperationType, BlockType } from '@/types/page';
import Bold from '@tiptap/extension-bold';
import HardBreak from '@tiptap/extension-hard-break';
import Text from '@tiptap/extension-text';
import {
  Node,
  NodeConfig,
  NodeViewContent,
  type NodeViewProps,
  NodeViewWrapper,
  ReactNodeViewRenderer,
  mergeAttributes,
} from '@tiptap/react';
import { useEffect } from 'react';
import { v4 as uuidv4 } from 'uuid';

type TextBlockAttributes = BlockAttributes;

const TextBlockConfig: NodeConfig<TextBlockAttributes, {}> = {
  name: BlockType.TEXT_BLOCK,
  group: 'block',
  content: 'inline*',
  addExtensions() {
    return [Text, HardBreak, Bold];
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
    return ReactNodeViewRenderer(Block);
  },
};

function Block(props: NodeViewProps) {
  return (
    <NodeViewWrapper>
      <BlockWrapper
        type={BlockType.TEXT_BLOCK}
        initialContent={{
          text: '',
        }}
        {...props}
      >
        <TextBlockComponent {...props} />
      </BlockWrapper>
    </NodeViewWrapper>
  );
}

export default Node.create(TextBlockConfig);
export type { TextBlockAttributes };
