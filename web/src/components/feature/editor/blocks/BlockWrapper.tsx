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
          className='absolute left-[-40px] top-0 opacity-0 group-hover:opacity-100 transition-opacity rounded'
        >
          <Button icon='ellipsis' variant='icon' tabIndex={-1} />
        </DropdownMenuTrigger>

        <DropdownMenuContent side='bottom' align='start'>
          {[...config, ...BaseBlockConfig].map(({ icon, label }, index) => {
            return (
              <DropdownMenuItem key={index} icon={icon}>
                {label}
              </DropdownMenuItem>
            );
          })}
        </DropdownMenuContent>
      </DropdownMenu>
      <div className='w-full'>{children}</div>
    </div>
  );
}

export default BlockWrapper;
