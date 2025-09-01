'use client';

import type { EditorProps } from '.';
import PageTitle from './PageTitle';
import { EditorBubbleMenu } from '@/components/feature/editor/EditorMenu';
import {
  CommandPaletteExtension,
  ImageNode,
} from '@/components/feature/editor/extensions';
import { useTransaction } from '@/components/provider/TransactionProvider';
import { usePageQuery } from '@/query/page';
import { EditorContent, type EditorEvents, useEditor } from '@tiptap/react';
import StarterKit from '@tiptap/starter-kit';
import { debounce } from 'lodash';
import { useEffect } from 'react';

export default function BasicJournalPageEditor({ pageId }: EditorProps) {
  const { data: page } = usePageQuery({ id: pageId });
  const { addTransaction } = useTransaction();

  const editor = useEditor({
    extensions: [StarterKit, CommandPaletteExtension, ImageNode],
    content: '',
    immediatelyRender: false,
    onTransaction: debounce(({ editor }: EditorEvents['transaction']) => {
      if (page) {
        addTransaction({
          pageId,
          content: JSON.stringify(editor.getJSON()),
        });
      }
    }, 3000),
  });

  useEffect(() => {
    if (editor && page) {
      try {
        editor.commands.setContent(JSON.parse(page.content));
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
