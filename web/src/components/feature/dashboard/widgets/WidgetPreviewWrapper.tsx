interface WidgetPreviewWrapperProps {
  children?: React.ReactNode;
}

export default function WidgetPreviewWrapper({
  children,
}: WidgetPreviewWrapperProps) {
  return (
    <div className='h-full w-full flex items-center justify-center'>
      <div className='h-full w-full flex items-center justify-center rounded-2xl shadow-lg'>
        {children}
      </div>
    </div>
  );
}
