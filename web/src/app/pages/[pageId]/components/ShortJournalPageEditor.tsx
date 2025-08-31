'use client';

import type { EditorProps } from '.';
import PageTitle from './PageTitle';
import { useTransaction } from '@/components/provider/TransactionProvider';
import { SHORT_JOURNAL_MAXIMUM_CONTENT_LENGTH } from '@/constant/editor';
import { usePageQuery } from '@/query/page';
import Document from '@tiptap/extension-document';
import Paragraph from '@tiptap/extension-paragraph';
import Text from '@tiptap/extension-text';
import { CharacterCount } from '@tiptap/extensions';
import { EditorContent, type EditorEvents, useEditor } from '@tiptap/react';
import { debounce } from 'lodash';
import { useEffect } from 'react';

export default function ShortJournalPageEditor({ pageId }: EditorProps) {
  const { data: page } = usePageQuery({ id: pageId });
  const { addTransaction } = useTransaction();

  const editor = useEditor({
    extensions: [
      Document,
      Paragraph,
      Text,
      CharacterCount.configure({
        limit: SHORT_JOURNAL_MAXIMUM_CONTENT_LENGTH,
      }),
    ],
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
    <div className='h-full w-full flex justify-center items-center'>
      <div className='w-96 h-96 flex flex-col'>
        <PageTitle pageId={pageId} />
        <EditorContent editor={editor} />
      </div>
    </div>
  );
}
