'use client';

import PageTitle from './PageTitle';
import { useEditorContext } from '@/components/feature/editor/EditorProvider';
import {
  CommandPaletteExtension,
  Document,
  GalleryBlock,
  MapBlock,
  TextBlock,
  deserializeBlocks,
} from '@/components/feature/editor/extensions';
import { usePageQuery } from '@/query/page';
import {
  type BlockOperation,
  BlockOperationType,
  BlockType,
} from '@/types/page';
import type { Transaction } from '@tiptap/pm/state';
import {
  EditorContent,
  type Editor as TiptapEditor,
  useEditor,
} from '@tiptap/react';
import type { Node } from 'prosemirror-model';
import { useEffect } from 'react';

export interface EditorProps {
  pageId: string;
}

export default function Editor({ pageId }: EditorProps) {
  const { data: page } = usePageQuery({ id: pageId });
  const { sendBlockOperations } = useEditorContext();

  const editor = useEditor({
    extensions: [
      Document,
      TextBlock.configure({ pageId }),
      GalleryBlock.configure({ pageId }),
      MapBlock.configure({ pageId }),
      CommandPaletteExtension,
    ],
    content: '',
    immediatelyRender: false,
    onTransaction: async ({ editor, transaction }) => {
      if (!transaction.docChanged) {
        return false;
      }

      const ops = [
        ...getDeletedBlocks(pageId, transaction.before, editor.state.doc),
        ...getUpdatedBlocks(editor, transaction),
      ];
      sendBlockOperations(ops);

      return false;
    },
  });

  useEffect(() => {
    if (editor && page) {
      editor.commands.setContent({
        type: 'doc',
        content: deserializeBlocks(page.pageId, page.blocks).filter(
          (content) => content !== null,
        ),
      });
    }
  }, [editor, page]);

  return (
    <>
      <PageTitle pageId={pageId} />
      <EditorContent editor={editor} />
    </>
  );
}

function getDeletedBlocks(
  pageId: string,
  previous: Node,
  current: Node,
): BlockOperation[] {
  const previousBlocks = new Set<string>();
  previous.descendants((block) => {
    if (block.isBlock) {
      previousBlocks.add(block.attrs.blockId);
    }
    return false;
  });

  const currentBlocks = new Set<string>();
  current.descendants((block) => {
    if (block.isBlock) {
      currentBlocks.add(block.attrs.blockId);
    }
    return false;
  });

  const deletedBlocks: string[] = [];
  previousBlocks.forEach((blockId) => {
    if (!currentBlocks.has(blockId)) {
      deletedBlocks.push(blockId);
    }
  });

  return deletedBlocks.map((blockId) => ({
    timestamp: Date.now(),
    op: BlockOperationType.DELETE,
    blockId,
    pageId,
  }));
}

function getUpdatedBlocks(
  editor: TiptapEditor,
  transaction: Transaction,
): BlockOperation[] {
  const modifiedRanges: { from: number; to: number }[] = [];
  transaction.selection.ranges.forEach((range) => {
    modifiedRanges.push({ from: range.$from.pos, to: range.$to.pos });
  });

  const updatedBlocks: BlockOperation[] = [];
  editor.state.doc.descendants((block, position) => {
    if (block.isBlock && block.attrs.ops.length > 0) {
      updatedBlocks.push(...block.attrs.ops);

      const { tr } = editor.state;
      tr.setNodeAttribute(position, 'ops', []);
      editor.view.dispatch(tr);
    }

    if (
      !block.isBlock ||
      modifiedRanges.every(
        (range) =>
          position > range.to || range.from > position + block.nodeSize,
      )
    ) {
      return false;
    }

    const type = block.type.name;

    if (type === BlockType.TEXT_BLOCK) {
      updatedBlocks.push({
        timestamp: Date.now(),
        op: BlockOperationType.UPDATE,
        blockId: block.attrs.blockId,
        pageId: block.attrs.pageId,
        type: BlockType.TEXT_BLOCK,
        content: {
          text:
            block.content.size > 0
              ? JSON.stringify(block.content.toJSON())
              : '',
        },
      });
    }
  });

  return updatedBlocks;
}
