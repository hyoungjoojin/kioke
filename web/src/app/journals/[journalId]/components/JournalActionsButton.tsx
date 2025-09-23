import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { IconName } from '@/components/ui/icon';
import { Input } from '@/components/ui/input';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@/components/ui/popover';
import { useClickAway } from '@uidotdev/usehooks';
import { useState } from 'react';

interface JournalActionsButtonProps {
  journalId: string;
}

export default function JournalActionsButton({
  journalId,
}: JournalActionsButtonProps) {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button icon={IconName.DOTS} variant='ghost' />
      </DropdownMenuTrigger>

      <DropdownMenuContent align='end' sideOffset={10}>
        <UpdateCoverButton />
        <DropdownMenuItem icon={IconName.TRASH}>
          Delete Journal
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}

function UpdateCoverButton() {
  const [isPopoverOpen, setIsPopoverOpen] = useState(false);

  const ref = useClickAway<HTMLDivElement>(() => {
    setIsPopoverOpen(false);
  });

  return (
    <>
      <DropdownMenuItem
        icon={IconName.IMAGE}
        onSelect={(e) => {
          e.preventDefault();
          setIsPopoverOpen(true);
        }}
      >
        Update Cover
      </DropdownMenuItem>

      <Popover open={isPopoverOpen}>
        <PopoverTrigger
          asChild
          onClick={() => {
            setIsPopoverOpen(true);
          }}
        >
          <span />
        </PopoverTrigger>
        <PopoverContent ref={ref} side='right' className='mx-1'>
          <span>Update Journal Cover</span>
          <span></span>

          <Input type='file' />
        </PopoverContent>
      </Popover>
    </>
  );
}
