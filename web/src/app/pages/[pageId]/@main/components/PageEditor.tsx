'use client';

import { useTransaction } from '@/components/provider/TransactionProvider';
import { Button } from '@/components/ui/button';
import Icon, { IconName } from '@/components/ui/icon';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { usePageQuery } from '@/query/page';
import { EditorContent, type EditorEvents, useEditor } from '@tiptap/react';
import { BubbleMenu } from '@tiptap/react/menus';
import StarterKit from '@tiptap/starter-kit';
import { debounce } from 'lodash';
import { useEffect } from 'react';

interface PageEditorProps {
  pageId: string;
}

export default function PageEditor({ pageId }: PageEditorProps) {
  const { data: page } = usePageQuery({ id: pageId });
  const { addTransaction } = useTransaction();

  const editor = useEditor({
    extensions: [StarterKit],
    content: '',
    immediatelyRender: false,
    onTransaction: debounce(({}: EditorEvents['transaction']) => {
      if (editor && pageId) {
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
      {editor && (
        <BubbleMenu editor={editor} options={{ placement: 'bottom' }}>
          <div className='flex gap-1 justify-center items-center'>
            <div className='rounded-full bg-white w-7 h-7 flex items-center justify-center'>
              <Popover>
                <PopoverTrigger asChild>
                  <Button variant='ghost'>
                    <Icon name={IconName.IMAGE} />
                  </Button>
                </PopoverTrigger>
                <PopoverContent>
                  <Tabs defaultValue='upload'>
                    <TabsList>
                      <TabsTrigger value='upload'>Upload Image</TabsTrigger>
                      <TabsTrigger value='link'>Link</TabsTrigger>
                    </TabsList>

                    <TabsContent value='upload'></TabsContent>
                    <TabsContent value='link'></TabsContent>
                  </Tabs>
                </PopoverContent>
              </Popover>
            </div>
          </div>
        </BubbleMenu>
      )}

      <EditorContent editor={editor} />
    </>
  );
}
