import { Button } from '@/components/ui/button';
import Icon, { IconName } from '@/components/ui/icon';
import { Input } from '@/components/ui/input';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import type { Editor } from '@tiptap/react';
import { BubbleMenu } from '@tiptap/react/menus';

interface EditorMenuProps {
  editor: Editor;
}

export function EditorBubbleMenu({ editor }: EditorMenuProps) {
  return (
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
              <ImageSelectionContent editor={editor} />
            </PopoverContent>
          </Popover>
        </div>
      </div>
    </BubbleMenu>
  );
}

export function EditorDropdownMenu() {
  return <div></div>;
}

function ImageSelectionContent({ editor }: { editor: Editor }) {
  return (
    <>
      <Tabs defaultValue='upload'>
        <TabsList>
          <TabsTrigger value='upload'>Upload Image</TabsTrigger>
          <TabsTrigger value='link'>Link</TabsTrigger>
        </TabsList>

        <TabsContent value='upload'>
          <Button onClick={() => {}}>Click here</Button>
          <Input type='file' />
        </TabsContent>
        <TabsContent value='link'></TabsContent>
      </Tabs>
    </>
  );
}
