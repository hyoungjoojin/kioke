import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { IconName } from '@/components/ui/icon';

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
        <DropdownMenuItem icon={IconName.TRASH}>
          Delete Journal
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
