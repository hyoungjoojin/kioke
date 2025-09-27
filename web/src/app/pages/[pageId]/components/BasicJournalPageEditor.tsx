'use client';

import type { EditorProps } from '.';
import PageTitle from './PageTitle';
import { EditorBubbleMenu } from '@/components/feature/editor/EditorMenu';
import {
  type BlockAttributes,
  Document,
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
    extensions: [Document, TextBlock],
    content: '',
    immediatelyRender: false,
    onTransaction: async ({
      editor,
      transaction,
    }: EditorEvents['transaction']) => {
      if (!page || !transaction.docChanged) {
        return;
      }

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
              position < range.to && range.from < position + block.nodeSize,
          )
        ) {
          const { blockId, isNew } = block.attrs as BlockAttributes;

          addTransaction({
            pageId,
            blockId,
            command: isNew ? 'create' : 'update',
            content: getBlockContent(block),
          });
        }

        return false;
      });
    },
    onDelete(props) {
      if (props.type !== 'node') {
        return;
      }

      const { blockId } = props.node.attrs as BlockAttributes;
      addTransaction({
        pageId,
        blockId,
        command: 'delete',
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
