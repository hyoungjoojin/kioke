import { useSettings } from './SettingsContext';
import { Button } from '@/components/ui/button';

interface SettingsEntryProps {
  title: string;
  children: React.ReactNode;
}

export function SettingsEntry({ title, children }: SettingsEntryProps) {
  return (
    <>
      <span className='font-bold'>{title}</span>
      {children}
    </>
  );
}

export function SettingsSubmit() {
  const { isUpdated, isPending, submit, cancel } = useSettings();

  if (!isUpdated) {
    return null;
  }

  return (
    <div className='flex gap-2'>
      <Button variant='destructive' onClick={() => cancel()}>
        Cancel
      </Button>
      <Button onClick={() => submit()} pending={isPending}>
        Submit
      </Button>
    </div>
  );
}
