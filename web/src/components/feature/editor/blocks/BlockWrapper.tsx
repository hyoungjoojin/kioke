import type { BlockAttributes } from '@/components/feature/editor/extensions';
import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import type { IconName } from '@/components/ui/icon';
import { BlockOperationType, type BlockType } from '@/types/page';
import type { NodeViewProps } from '@tiptap/react';
import { useEffect } from 'react';
import { v4 as uuidv4 } from 'uuid';

type BlockWrapperProps = NodeViewProps & {
  type: BlockType;
  initialContent: any;
  config?: BlockConfig[];
  children: React.ReactNode;
};

type BlockConfig = {
  icon: IconName;
  label: string;
  disabled?: boolean;
};

const BaseBlockConfig: BlockConfig[] = [
  {
    icon: 'trash',
    label: 'Delete Block',
  },
];

function BlockWrapper({
  node,
  updateAttributes,
  config = [],
  type,
  initialContent,
  children,
}: BlockWrapperProps) {
  const { pageId, blockId, ops } = node.attrs as BlockAttributes;

  useEffect(() => {
    if (blockId === null) {
      setTimeout(() => {
        const blockId = uuidv4();

        updateAttributes({
          pageId,
          blockId,
          ops: [
            ...ops,
            {
              timestamp: Date.now(),
              op: BlockOperationType.UPDATE,
              pageId,
              blockId,
              type,
              content: initialContent,
            },
          ],
        } satisfies Partial<BlockAttributes>);
      }, 0);
    }
  }, [blockId, pageId, ops, updateAttributes, type, initialContent]);

  return (
    <div className='relative group flex items-center'>
      <DropdownMenu>
        <DropdownMenuTrigger
          asChild
          className='absolute left-[-32px] top-4 opacity-0 group-hover:opacity-100 transition-opacity p-1 rounded'
        >
          <Button icon='ellipsis-vertical' variant='icon' tabIndex={-1} />
        </DropdownMenuTrigger>

        <DropdownMenuContent side='left' align='end'>
          {[...config, ...BaseBlockConfig].map(({ label }, index) => {
            return <DropdownMenuItem key={index}>{label}</DropdownMenuItem>;
          })}
        </DropdownMenuContent>
      </DropdownMenu>
      <div className='w-full'>{children}</div>
    </div>
  );
}

export default BlockWrapper;
