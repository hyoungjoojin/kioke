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
      <div>{children}</div>
    </>
  );
}

export function SettingsSubmit() {
  const { isDirty, submit, cancel } = useSettings();

  if (!isDirty) {
    return null;
  }

  return (
    <div className='flex gap-2'>
      <Button variant='destructive' onClick={() => cancel()}>
        Cancel
      </Button>
      <Button onClick={() => submit()}>Submit</Button>
    </div>
  );
}
