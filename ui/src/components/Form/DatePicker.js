import React from 'react'
import { TextField } from '@material-ui/core'
import { useField } from 'formik'


const FormikNativeDatePicker = ({ label, ...props }) => {
  const [field, meta] = useField(props)

  return (
    <TextField
      {...field}
      {...props}
      type="date"
      label={label}
      InputLabelProps={{ shrink: true }}
      variant="outlined"
      fullWidth
      error={meta.touched && Boolean(meta.error)}
      helperText={meta.touched && meta.error}
    />
  )
}

export default FormikNativeDatePicker
