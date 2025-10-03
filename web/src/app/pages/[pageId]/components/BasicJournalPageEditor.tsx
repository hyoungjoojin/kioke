'use client';

import type { EditorProps } from '.';
import PageTitle from './PageTitle';
import { EditorBubbleMenu } from '@/components/feature/editor/EditorMenu';
import {
  type BlockAttributes,
  CommandPaletteExtension,
  Document,
  ImageBlock,
  MapBlock,
  TextBlock,
  deserializeBlock,
  getBlockContent,
} from '@/components/feature/editor/extensions';
import { useTransaction } from '@/components/provider/TransactionProvider';
import { usePageQuery } from '@/query/page';
import { EditorContent, type EditorEvents, useEditor } from '@tiptap/react';
import { useEffect } from 'react';

export default function BasicJournalPageEditor({ pageId }: EditorProps) {
  const { data: page } = usePageQuery({ id: pageId });
  const { addTransaction } = useTransaction();

  const editor = useEditor({
    extensions: [
      Document,
      TextBlock,
      ImageBlock,
      MapBlock,
      CommandPaletteExtension,
    ],
    content: '',
    immediatelyRender: false,
    onTransaction: async ({
      editor,
      transaction,
    }: EditorEvents['transaction']) => {
      if (!page || !transaction.docChanged) {
        return;
      }

      const previousBlocks = new Set<string>();
      transaction.before.descendants((block) => {
        if (block.isBlock) {
          previousBlocks.add(block.attrs.blockId);
        }
      });

      const currentBlocks = new Set<string>();
      editor.state.doc.descendants((block) => {
        if (block.isBlock) {
          currentBlocks.add(block.attrs.blockId);
        }
      });

      [...previousBlocks]
        .filter((block) => !currentBlocks.has(block))
        .forEach((blockId) => {
          addTransaction({
            pageId,
            blockId,
            command: 'delete',
          });
        });

      const modifiedRanges: { from: number; to: number }[] = [];
      transaction.steps.forEach((_, i) => {
        transaction.mapping.maps[i].forEach((from, to) => {
          modifiedRanges.push({ from, to });
        });
      });

      editor.state.doc.descendants((block, position) => {
        if (!block.isBlock) {
          return false;
        }

        if (
          modifiedRanges.some(
            (range) =>
              position <= range.to && range.from <= position + block.nodeSize,
          )
        ) {
          const { blockId, isNew } = block.attrs as BlockAttributes;

          addTransaction({
            pageId,
            blockId,
            command: isNew ? 'create' : 'update',
            content: getBlockContent(block),
          });

          if (isNew) {
            const { tr } = editor.state;
            tr.setNodeAttribute(position, 'isNew', false);
            editor.view.dispatch(tr);
          }
        }

        return false;
      });
    },
  });

  useEffect(() => {
    if (editor && page) {
      editor.commands.setContent({
        type: 'doc',
        content: page.blocks
          .map((block) => deserializeBlock(block))
          .filter((content) => content !== null),
      });
      try {
      } catch (_) {
        editor.commands.setContent('');
      }
    }
  }, [page, editor]);

  return (
    <>
      <PageTitle pageId={pageId} />
      {editor && <EditorBubbleMenu editor={editor} />}
      <EditorContent editor={editor} />
    </>
  );
}
