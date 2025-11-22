import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';

function Page() {
  return <></>;
}

function PageOptions() {
  function ChangeDateItem() {
    return <DropdownMenuItem icon='calendar'>Change Date</DropdownMenuItem>;
  }

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant='icon' icon='ellipsis' />
      </DropdownMenuTrigger>
      <DropdownMenuContent align='end'>
        <ChangeDateItem />
      </DropdownMenuContent>
    </DropdownMenu>
  );
}

export default Page;
export { PageOptions };
